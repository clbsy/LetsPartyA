package parteam.letspartya;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * Created by miaomiao on 2015/12/20.
 */
public class Utils {
    public static DisplayImageOptions getDefaultImageOptions(int resourceId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(resourceId)
                .showImageForEmptyUri(resourceId)
                .showImageOnFail(resourceId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public static void displayImage(ImageView imageView, String url, DisplayImageOptions options) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().cancelDisplayTask(imageView);
            imageView.setImageDrawable(null);
            imageView.setTag(R.id.image_url, url);
            return;
        }

        String oldUrl = String.valueOf(imageView.getTag(R.id.image_url));
        if (url.equals(oldUrl)) {
            return;
        }

        imageView.setTag(R.id.image_url, url);
        ImageLoader.getInstance().cancelDisplayTask(imageView);
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void displayImage(ImageView imageView, int resourceId, DisplayImageOptions options) {
        String url = "drawable://" + resourceId;
        displayImage(imageView, url, options);
    }
}
