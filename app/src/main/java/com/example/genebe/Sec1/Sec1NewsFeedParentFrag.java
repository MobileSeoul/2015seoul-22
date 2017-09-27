package com.example.genebe.Sec1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.genebe.R;

/**
 * Created by 사하앍 on 2015-06-05.
 */
public class Sec1NewsFeedParentFrag extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private FragmentTabHost mTabHost;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Sec1NewsFeedParentFrag newInstance(int sectionNumber) {
        Sec1NewsFeedParentFrag fragment = new Sec1NewsFeedParentFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Sec1NewsFeedParentFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*내부 탭 추가*/
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabContent);
        mTabHost.addTab(mTabHost.newTabSpec("followers").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.tab_title_followers)),
                Sec1NewsFeedFollowers.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("random").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.tab_title_random)),
                Sec1NewsFeedRandom.class, null);
        mTabHost.getTabWidget().setDividerDrawable(null);
        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    private View getTabIndicator(Context context, int title) {
        View view = LayoutInflater.from(context).inflate(R.layout.sec1_nestedtab_layout, null);
        TextView nestedtabtitle = (TextView) view.findViewById(R.id.Sec1tabtextView);
        nestedtabtitle.setText(title);
        return view;
    }
}