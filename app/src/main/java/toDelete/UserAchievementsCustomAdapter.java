package toDelete;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.AllRewardsActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.R;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class UserAchievementsCustomAdapter extends BaseAdapter implements View.OnClickListener {




    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    UserAchievementModel tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public UserAchievementsCustomAdapter(Activity a, ArrayList<UserAchievementModel> d,Resources resLocal) {
        activity = a;
        data = (ArrayList) d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView tvName;
        public TextView tvDescription;
        public ImageView ivMerchantLogo;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            //vi = inflater.inflate(R.layout.user_achievements_view, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tvName = (TextView) vi.findViewById(R.id.merchant_name);
            holder.tvDescription = (TextView)vi.findViewById(R.id.merchant_desc);
            holder.ivMerchantLogo = (ImageView)vi.findViewById(R.id.merchant_image);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0)
        {
            holder.tvName.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (UserAchievementModel) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.tvName.setText(tempValues.getAchievementName());
            holder.tvDescription.setText(tempValues.getAchievementDescription());
/*
            if (URLUtil.isValidUrl(tempValues.getMerchantLogoURL()))
            {
                try {
                    holder.ivMerchantLogo.setImageBitmap(BitmapFactory.decodeStream((InputStream) new URL(tempValues.getMerchantLogoURL()).getContent()));
                    //holder.ivMerchantLogo.setImageResource(BitmapFactory.decodeStream((new URL(tempValues.getMerchantLogoURL())).openConnection().getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                holder.ivMerchantLogo.setImageResource(
                        res.getIdentifier(
                                "@mipmap/no_company"
                                , null, null));
            }
*/
            //profile_photo.setImageBitmap(BitmapFactory.decodeStream((new URL()).openConnection().getInputStream()));



            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            AllRewardsActivity sct = (AllRewardsActivity)activity;

            /****  Call  onItemClick Method inside AllRewardsActivity Class ( See Below )****/

            //sct.onItemClick(mPosition);
        }
    }














}
