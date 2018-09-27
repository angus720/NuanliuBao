package nuanliu.com.modao.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nuanliu.com.modao.widget.convenientbanner.holder.Holder;


/**
 * Created by Deity on 2016/12/15.
 */
//先用本地的随时改成网络的
public class HomeBanner implements Holder<Integer> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        Glide.with(context).load(data).into(imageView);

    }
}
