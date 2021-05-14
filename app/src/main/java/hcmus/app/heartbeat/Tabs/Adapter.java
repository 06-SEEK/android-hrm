package hcmus.app.heartbeat.Tabs;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class Adapter  extends FragmentPagerAdapter {
    private String tabsList [] = {"HELP","MEASURE","HISTORY"};
    private HistoryMeasureFragment historyMeasureFragment;
    private HelpFragment helpFragment;
    private MeasureFragment measureFragment;

    public Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            helpFragment = new HelpFragment();
            return helpFragment;
        }else if(position==1){
            measureFragment = new MeasureFragment();
            return measureFragment;
        }else if(position==2){
            historyMeasureFragment = new HistoryMeasureFragment();
            return historyMeasureFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabsList.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "HELP";
                break;
            case 1:
                title = "MEASURE";
                break;
            case 2:
                title = "HISTORY";
                break;
        }
        return title;
    }
}
