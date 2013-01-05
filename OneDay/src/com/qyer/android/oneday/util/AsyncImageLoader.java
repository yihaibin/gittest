package com.qyer.android.oneday.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 
 * @author yhb
 * 
 */
public class AsyncImageLoader {

	public static final String TAG = "AsyncImageLoader";
	
	public static final String SCHEME_FILE = "file";
	public static final String SCHEME_HTTP = "http";
	public static final String SCHEME_HTTPS = "https";

	private static final int HANDLE_IMAGE_CALLBACK = 0;
	private static final int DEF_POOL_SIZE = 1;

	private ExecutorService mExecutorService;
	private Map<String,SoftReference<Drawable>> mImageCache;
	private boolean mIsRelease;
	private int mPoolSize = DEF_POOL_SIZE;
	private String mIamgePrefix = "";
	private File mImageSaveDir;
	private Drawable mDefDrawable;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case HANDLE_IMAGE_CALLBACK:
					// callback image loaded
					handleImageCallbackMsg((ImageCallbackWrapper)msg.obj);
					break;
			}
		};
	};

	public AsyncImageLoader()
	{
		this(DEF_POOL_SIZE);
	}
	
	public AsyncImageLoader(int poolSize) 
	{
		mPoolSize = poolSize;
		mImageCache = new HashMap<String, SoftReference<Drawable>>();
		mExecutorService = Executors.newFixedThreadPool(mPoolSize);
	}
	
	public void setDefaultDrawable(Drawable drawable)
	{
		mDefDrawable = drawable;
	}
	
	public void setImagePrefix(String prefix) {
		if (prefix == null)
			prefix = "";

		mIamgePrefix = prefix;
	}

	public void setImageSaveDir(String dir) {
		if (dir != null)
			mImageSaveDir = new File(dir);
	}
	
	public void release(){
		mIsRelease = true;
		mImageCache.clear();
		mExecutorService.shutdown();
		mDefDrawable = null;
	}
	
	public Drawable asyncImageLoad(String imageName, ImageCallback imageCallback) 
	{
		return asyncImageLoad(imageName, imageCallback, true);
	}
	
	public Drawable asyncImageLoad(String imageName, ImageCallback imageCallBack, boolean isCache)
	{
		if (mIsRelease)
			return null;

		if (TextUtils.isEmpty(imageName) || imageCallBack == null)
			return null;
		
		Drawable drawable = getDrawableFromImageCache(imageName);
		if (drawable != null)
			return drawable;
			
		mExecutorService.execute(new ImageLoadTask(new ImageCallbackWrapper(imageName, imageCallBack, isCache)));
		return mDefDrawable;		
	}
	
	private Drawable getDrawableFromImageCache(String imageName) {

		SoftReference<Drawable> srd = mImageCache.get(imageName);
		if (srd == null) {
			
			return null;
		} else {
			
			Drawable d = srd.get();
			if (d == null)
				mImageCache.remove(imageName);

			return d;
		}
	}
	
	private void putDrawableToImageCache(String imageName, Drawable drawable) {

		if(drawable != null)
			mImageCache.put(imageName, new SoftReference<Drawable>(drawable));
	}

	private void sendImageCallbackMsg(ImageCallbackWrapper icw) {
		
		Message msg = mHandler.obtainMessage(HANDLE_IMAGE_CALLBACK, icw);
		mHandler.sendMessage(msg);
	}

	private void handleImageCallbackMsg(ImageCallbackWrapper icw) {
		
		if (mIsRelease)
			return;
		
		if(icw.isCache)
			putDrawableToImageCache(icw.imageName, icw.drawable);
		
		icw.callback.onImageLoaded(icw.imageName, icw.drawable);
	}

	private static class ImageCallbackWrapper {
		
		String imageName;
		Drawable drawable;
		ImageCallback callback;
		boolean isCache;

		ImageCallbackWrapper(String imagePath, ImageCallback callback, boolean isCache) {
			
			this.imageName = imagePath;
			this.callback = callback;
			this.isCache = isCache;
		}
	}

	public interface ImageCallback {
		
		public void onImageLoaded(String imagePath, Drawable drawable);
	}
	
	private class ImageLoadTask implements Runnable {
		
		ImageCallbackWrapper mIcw;
		
		ImageLoadTask(ImageCallbackWrapper icw){
			mIcw = icw;
		}
		
		@Override
		public void run() {
			// loadeImage...
			mIcw.drawable = loadImage(Uri.parse(mIamgePrefix + mIcw.imageName));
			sendImageCallbackMsg(mIcw);
		}

		private Drawable loadImage(Uri uri) {
			
			Bitmap bmp = null;
			String scheme = uri.getScheme();
			if (scheme == null || scheme.equals(SCHEME_FILE)) {

				bmp = loadBitmapFromFile(new File(uri.getPath()));

			} else if (SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme)) {
				
				String hashCodeName = uri.toString().hashCode() + "";
				bmp = loadImageFromSaveDir(hashCodeName);
				if (bmp == null) {
					bmp = loadBitmapFromUrl(uri.toString());
					if (bmp != null)
						saveToImageDir(bmp, hashCodeName);
				}
			}

			return createDrawableBitmap(bmp);
		}

		private Drawable createDrawableBitmap(Bitmap bmp) {
			
			if (bmp == null)
				return null;

			Drawable d = new BitmapDrawable(bmp);
			d.setDither(false);
			d.setFilterBitmap(false);
			return d;
		}

		private Bitmap loadImageFromSaveDir(String imageName) {
			if (mImageSaveDir == null)
				return null;

			return loadBitmapFromFile(new File(mImageSaveDir, imageName));
		}

		private void saveToImageDir(Bitmap bmp, String hashCodeName) {
			if (mImageSaveDir == null)
				return;

			storeBitmap(bmp, new File(mImageSaveDir, hashCodeName), 100);
		}

		/*
		 * Util method
		 */
		private Bitmap loadBitmapFromFile(File imageFile) {
			InputStream input = null;
			Bitmap bmp = null;
			try {
				if (!imageFile.exists())
					return null;

				input = new FileInputStream(imageFile);
				bmp = BitmapFactory.decodeStream(input);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeInStream(input);
			}

			return bmp;
		}

		public Bitmap loadBitmapFromUrl(String url) {
			if (TextUtils.isEmpty(url))
				return null;

			Bitmap bmp = null;
			InputStream input = null;

			try {

				URL urlObj = new URL(url);
				input = (InputStream) urlObj.getContent();
				bmp = BitmapFactory.decodeStream(input);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeInStream(input);
			}

			return bmp;
		}

		private boolean storeBitmap(Bitmap bitmap, File saveFile, int quality) {
			if (bitmap == null || saveFile == null)
				return false;

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(saveFile);
				bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
				return true;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeOutStream(fos);
			}
			return false;
		}

		private void closeInStream(InputStream input) {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void closeOutStream(OutputStream output) {
			try {
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
