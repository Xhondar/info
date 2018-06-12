package esame.progetto.xhondar.github.com.info;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class LocalListAdapter extends ArrayAdapter<Local> {

    private static final String TAG = "LocalListAdapter";

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView name;
        TextView address;
        TextView number;
        TextView description;
        ImageView img_local;
        ImageView img_star;
    }

    public LocalListAdapter(Context context, int resource, ArrayList<Local> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        setupImageLoader();

        String name = getItem(position).getName();
        String address = getItem(position).getAddress();
        String number = getItem(position).getNumber();
        String description = getItem(position).getDescription();
        String img_local = getItem(position).getImg_local();
        String img_star = getItem(position).getImg_star();

        final View result;

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.img_local = (ImageView) convertView.findViewById(R.id.image2);
            holder.img_star = (ImageView) convertView.findViewById(R.id.image1);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.name.setText(name);
        holder.address.setText(address);
        holder.number.setText(number);
        holder.description.setText(description);

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoader imageLoader2 = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed", null, mContext.getPackageName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(img_local, holder.img_local, options);
        imageLoader2.displayImage(img_star, holder.img_star, options);

        return convertView;
    }

    private void setupImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }
}