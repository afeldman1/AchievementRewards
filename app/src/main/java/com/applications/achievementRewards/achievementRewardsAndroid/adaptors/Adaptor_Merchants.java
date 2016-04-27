package com.applications.achievementRewards.achievementRewardsAndroid.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.R;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Feldman on 4/9/2016.
 */
public class Adaptor_Merchants extends BaseAdapter implements Filterable {
    Context context;
    List<MerchantModel> merchantModelsUnfiltered, merchantModels;
    private static LayoutInflater inflater = null;

    public Adaptor_Merchants(Context context, List<MerchantModel> merchantModels) {
        this.context = context;
        this.merchantModelsUnfiltered = merchantModels;
        this.merchantModels = merchantModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return merchantModels.size();
    }

    @Override
    public Object getItem(int position) {
        return merchantModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.composite_merchants, parent, false);
        }

        MerchantModel merchantModel = (MerchantModel) getItem(position);

        if (merchantModel.getLogoUrl() != null) {
            ImageView merchant_logo_iv = (ImageView) convertView.findViewById(R.id.merchant_logo_iv);
            Picasso.with(context).load(merchantModel.getLogoUrl().toString()).into(merchant_logo_iv);
        }

        TextView merchant_tv = (TextView) convertView.findViewById(R.id.merchant_tv);
        merchant_tv.setText(merchantModel.getMerchantName());

        return convertView;
    }

    @SuppressWarnings("redundant")
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                List<MerchantModel> filteredMerchantModels = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                merchantModels = merchantModelsUnfiltered;

                for (MerchantModel merchantModel : merchantModels) {
                    if (merchantModel.getMerchantName().toLowerCase().contains(constraint) ||
                            (merchantModel.getMerchantDescription() != null && merchantModel.getMerchantDescription().toLowerCase().contains(constraint))) {
                        filteredMerchantModels.add(merchantModel);
                    }
                }

                results.count = filteredMerchantModels.size();
                results.values = filteredMerchantModels;

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                merchantModels = (List<MerchantModel>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
