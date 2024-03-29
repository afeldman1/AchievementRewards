package toDelete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.applications.achievementRewards.achievementRewardsAndroid.AchievementDetailsActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.CompositeRewardActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.DisplayLocation;
import com.applications.achievementRewards.achievementRewardsAndroid.NavigationViewActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.R;
import com.applications.achievementRewards.achievementRewardsAndroid.SettingsActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.CurrentUserModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class AllRewardsActivity extends NavigationViewActivity {
    private CurrentUserModel currUser;

    //ListView list;
    //UserAchievementsCustomAdapter adapter;
    //public AllRewardsActivity CustomListView = null;
    public  List<UserAchievementModel> userAchievementData;

    public ArrayList<CompositeRewardActivity> all_companies = new ArrayList<CompositeRewardActivity>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rewards_screen);

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //new UserAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Button btnOne = (Button)findViewById(R.id.submitButton);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText) findViewById(R.id.editText);
                String tmp;
                tmp = et1.getText().toString();
                int tmp2 = Integer.parseInt(tmp);
                LinearLayout ml = (LinearLayout) findViewById(R.id.company_list);
                ml.removeAllViews();
                all_companies.clear();
                for (int i = 0; i < tmp2; i++) {
                    CompositeRewardActivity cmp = new CompositeRewardActivity(AllRewardsActivity.this);
                    cmp.setLayoutParams(new CompositeRewardActivity.LayoutParams(CompositeRewardActivity.LayoutParams.WRAP_CONTENT, CompositeRewardActivity.LayoutParams.WRAP_CONTENT));
                    cmp.setClickable(true);
                    cmp.setCompanyName("Company" + i);
                    all_companies.add(cmp);
                    ml.addView(cmp, i);

                }

                //This doesn't work yet sadly
                for (int i = 0; i < all_companies.size(); i++) {
                    CompositeRewardActivity cmp1 = (CompositeRewardActivity) all_companies.get(i);
                    cmp1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("HI");
                            Intent intent = new Intent(getApplicationContext(), AchievementDetailsActivity.class);
                            startActivityForResult(intent, 100);
                        }
                    });
                }


            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    // This method will be called when a CurrentUserModel is posted
    @Subscribe
    public void onEvent(List<UserAchievementModel> userAchievementData){

        UserAchievementModel userAchievementModel;
        //setContentView(R.layout.user_achievements_view);

        this.userAchievementData = userAchievementData;

        //CustomListView = this;

        //Resources res = getResources();
        //ListView lv= ( ListView )findViewById( R.id.listView );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        //adapter = new UserAchievementsCustomAdapter(this, (ArrayList)userAchievementData,res );
        //list.setAdapter(adapter );

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /*****************  This function used by adapter ****************/
    /*public void onItemClick(int mPosition)
    {
        // SHOW ALERT

        //System.out.println("HI");
        UserAchievementModel tempValues = userAchievementData.get(mPosition);

        Intent intent = new Intent(getApplicationContext(), RewardDetailsActivity.class);
        startActivityForResult(intent, 100);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    Intent intent = new Intent(this, SettingsActivity.class);
        //    startActivity(intent);
        //    return true;
        //} else if (id == R.id.action_test) {
        //    Intent intent = new Intent(this, DisplayLocation.class);
        //    startActivity(intent);
        //    return true;
        //}


        return super.onOptionsItemSelected(item);
    }

}
