package com.example.levent_j.dotamin_.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levent_j.dotamin_.R;
import com.example.levent_j.dotamin_.pojo.MatchPlayer;
import com.example.levent_j.dotamin_.pojo.PlayerDetailBean;
import com.example.levent_j.dotamin_.utils.Heroes;
import com.example.levent_j.dotamin_.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-3-9.
 */
public class DetailsAdapter extends BaseExpandableListAdapter {
    private TextView heroname;
    private TextView herolevel;
    private TextView heroteam;
    private TextView herokill;
    private TextView herodeath;
    private TextView heroass;
    private ImageView avater;


    private TextView hits;
    private TextView denis;
    private TextView gpm;
    private TextView xpm;
    private TextView heroDamage;
    private TextView towerDamage;
    private TextView heroHealing;
    private TextView kda;
    private TextView fight;

    private Context context;
    private List<MatchPlayer> matchPlayerList;
    private Map<MatchPlayer,List<PlayerDetailBean>> matchPlayerListMap;


    public DetailsAdapter(List<MatchPlayer> players,Map<MatchPlayer,List<PlayerDetailBean>> map,Context mcontext){
        this.context = mcontext;
        this.matchPlayerList = players;
        this.matchPlayerListMap = map;
    }

    @Override
    public int getGroupCount() {
        return matchPlayerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        MatchPlayer key = matchPlayerList.get(groupPosition);
        int size = matchPlayerListMap.get(key).size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return matchPlayerList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        MatchPlayer key = matchPlayerList.get(groupPosition);
        return matchPlayerListMap.get(key).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_parent,null);
        }

        heroname = (TextView) convertView.findViewById(R.id.tv_hero_name);
        herolevel = (TextView) convertView.findViewById(R.id.tv_hero_level);
        heroteam = (TextView) convertView.findViewById(R.id.tv_hero_team);
        herokill = (TextView) convertView.findViewById(R.id.tv_hero_kill);
        herodeath = (TextView) convertView.findViewById(R.id.tv_hero_death);
        heroass = (TextView) convertView.findViewById(R.id.tv_hero_ass);
        avater = (ImageView) convertView.findViewById(R.id.iv_hero_avater);

        MatchPlayer matchPlayer = matchPlayerList.get(groupPosition);
        heroname.setText(Heroes.HERO_NAME[matchPlayer.getHero_id() - 1]);
        herolevel.setText("等级"+matchPlayer.getLevel());
        heroteam.setText((Util.isRadiant(matchPlayer.getPlayerSlot()))?"天辉":"夜魇");
        herokill.setText("击杀："+matchPlayer.getKills());
        herodeath.setText("死亡："+matchPlayer.getDeaths());
        heroass.setText("助攻：" + matchPlayer.getAssists());
        Picasso.with(context).load(Heroes.HERO_IMAGE_FULL[matchPlayer.getHero_id() - 1]).into(avater);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MatchPlayer key = matchPlayerList.get(groupPosition);
        PlayerDetailBean info = matchPlayerListMap.get(key).get(childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_children,null);
        }

        hits = (TextView) convertView.findViewById(R.id.tv_hits);
        denis = (TextView) convertView.findViewById(R.id.tv_denis);
        xpm = (TextView) convertView.findViewById(R.id.tv_XPM);
        gpm = (TextView) convertView.findViewById(R.id.tv_GPM);
        heroDamage = (TextView) convertView.findViewById(R.id.tv_hero_damage);
        towerDamage = (TextView) convertView.findViewById(R.id.tv_tower_damage);
        heroHealing = (TextView) convertView.findViewById(R.id.tv_hero_healing);
        kda = (TextView) convertView.findViewById(R.id.tv_hero_kda);
        fight = (TextView) convertView.findViewById(R.id.tv_hero_fight);

        hits.setText("正补数："+info.getHits());
        denis.setText("反补数：" + info.getDenies());
        xpm.setText("XPM：" + info.getXPM());
        gpm.setText("GPM：" + info.getGPM());
        heroDamage.setText("英雄伤害：" + info.getHeroDamage());
        towerDamage.setText("建筑伤害：" + info.getTowerDamage());
        heroHealing.setText("治疗量：" + info.getHealing());
        kda.setText("KDA："+info.getKda());
        fight.setText("参战率："+info.getFight()+"%");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}