package com.sungbo.dustinfo;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sungbo.dustinfo.common.AddLocationDialogFragment;
import com.sungbo.dustinfo.db.LocationRealmObject;
import com.sungbo.dustinfo.finedust.FineDustFragment;
import com.sungbo.dustinfo.util.GeoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_LOCATION = 1;
    private ArrayList<Pair<Fragment, String>> mFragmentList;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Realm mRealm;
    private ImageView mSearchButton;
    private EditText mCityName;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    // 사용자 위치 수신기
    private LocationManager locationManager;
    private LocationListener locationListener;

    String[] cities = {"서울특별시", "부산광역시", "인천광역시", "대구광역시", "광주광역시", "수원시", "울산광역시", "고양시", "용인시",
            "창원시", "성남시", "청주시", "부천시", "남양주시", "전주시", "천안시", "안산시", "안양시", "김해시", "평택시",
            "포항시", "제주시", "시흥시", "파주시", "의정부시", "김포시", "구미시", "광주시", "양산시", "원주시", "진주시",
            "세종특별자치시", "광명시", "아산시", "춘천시", "경산시", "군포시", "군산시", "하남시", "여수시", "순천시", "경주시",
            "거제시", "목포시", "오산시", "이천시", "강릉시", "양주시", "충주시", "안성시", "구리시", "서산시", "서귀포시", "당진시",
            "안동시", "포천시", "의왕시", "광양시", "김천시", "제천시", "통영시", "논산시", "칠곡군", "사천시", "여주시", "공주시",
            "양평군", "정읍시", "영주시", "나주시", "음성군", "밀양시", "홍성군", "보령시", "완주군", "상주시", "영천시", "동두천시",
            "동해시", "김제시", "무안군", "남원시", "진천군", "예산군", "속초시", "문경시", "함안군", "삼척시", "홍천군", "해남군",
            "부여군", "창녕군", "태안군", "고흥군", "화순군", "거창군", "가평군", "영암군", "금산군", "고창군", "과천시", "서천군",
            "고성군", "부안군", "의성군", "옥천군", "영광군", "영동군", "울진군", "완도군", "예천군", "철원군", "태백시", "연천군",
            "담양군", "합천군", "하동군", "횡성군", "남해군", "계롱시", "장성군", "청도군", "성주군", "평창군", "보성군", "괴산군",
            "함양군", "증평군", "영월군", "장흥군", "영덕군", "정선군", "신안군", "산청군", "강진군", "고령군", "보은군", "청양군",
            "봉화군", "함평군", "인제군", "진도군", "곡성군", "고성군", "단양군", "순창군", "임실군", "의령군", "양양군", "화천군", "청송군", "구례군",
            "무주군", "진안군", "양구군", "군위군", "장수군", "영양군", "울릉군"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchButton = (ImageView) findViewById(R.id.search_button);
        mCityName = (EditText) findViewById(R.id.main_search);


//        // 리스트를 활용한 autocomplete
//        mCityName.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, cities) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView textView = (TextView) super.getView(position, convertView, parent);
//                textView.setBackgroundColor(Color.WHITE);
//                return textView;
//            }
//        });


        // google place autocomplete api
        //initialize places
        Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_API_KEY);

        //set edittext non focusable
        mCityName.setFocusable(false);
        mCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityName.getText().toString() != null) {
                    final String city = mCityName.getText().toString();
                    GeoUtil.getLocationFromName(MainActivity.this, city, new GeoUtil.GeoUtilListener() {
                        @Override
                        public void onSuccess(double lat, double lng) {
                            saveNewCity(lat, lng, city);
                            addNewFragement(lat, lng, city);

                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "지역을 선택하여 주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



        mRealm = Realm.getDefaultInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLocationDialogFragment.newInstance(new AddLocationDialogFragment.OnClickListener() {
                    @Override
                    public void onOkClicked(final String city) {
                        GeoUtil.getLocationFromName(MainActivity.this, city, new GeoUtil.GeoUtilListener() {
                            @Override
                            public void onSuccess(double lat, double lng) {
                                saveNewCity(lat, lng, city);
                                addNewFragement(lat, lng, city);

                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show(getSupportFragmentManager(), "dialog");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpViewPager();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final Place place = Autocomplete.getPlaceFromIntent(data);

                GeoUtil.getLocationFromName(MainActivity.this, place.getName(), new GeoUtil.GeoUtilListener() {
                    @Override
                    public void onSuccess(double lat, double lng) {
                        saveNewCity(lat, lng, place.getName());
                        addNewFragement(lat, lng, place.getName());

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });



                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("autocomplete", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.d("autocomplete", "cancelled");

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        loadDbData();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void loadDbData() {

//        settingGPS();
//        Location userLocation = getMyLocation();
//
//        double latitude = 0;
//        double longitude = 0;
//        if (userLocation != null) {
//            latitude = userLocation.getLatitude();
//            longitude = userLocation.getLongitude();
//        }
//
//
//        mFragmentList = new ArrayList<>();
//        mFragmentList.add(new Pair<Fragment, String>(
//                new FineDustFragment().newInstance(latitude, longitude), "현재 위치"
//        ));

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new Pair<Fragment, String>(
                new FineDustFragment(), "현재 위치"
        ));


        RealmResults<LocationRealmObject> realmResults =
                mRealm.where(LocationRealmObject.class).findAll();
        for (LocationRealmObject realmObjet : realmResults) {
            mFragmentList.add(new Pair<Fragment, String>(
                    new FineDustFragment().newInstance(realmObjet.getLat(), realmObjet.getLng()), realmObjet.getName()
            ));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void saveNewCity(double lat, double lng, String city){
        mRealm.beginTransaction();
        LocationRealmObject newLocationRealmObject =
                mRealm.createObject(LocationRealmObject.class);
        newLocationRealmObject.setName(city);
        newLocationRealmObject.setLat(lat);
        newLocationRealmObject.setLng(lng);
        mRealm.commitTransaction();
    }

    private void addNewFragement(double lat, double lng, String city){
        mFragmentList.add(new Pair<Fragment, String>(FineDustFragment.newInstance(lat, lng), city));
        Log.d("main activity", "new fragment added");
        mViewPager.getAdapter().notifyDataSetChanged();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_all_delete) {
            mRealm.beginTransaction();
            mRealm.where(LocationRealmObject.class).findAll().deleteAllFromRealm();
            mRealm.commitTransaction();

            //화면 재로딩
            setUpViewPager();
            return true;
        }
        else if (id == R.id.action_delete){
            if(mTabLayout.getSelectedTabPosition() == 0){
                Toast.makeText(this, "현재 탭은 삭제할 수 없습니다", Toast.LENGTH_SHORT).show();
            }
            mRealm.beginTransaction();
            mRealm.where(LocationRealmObject.class).findAll()
                    .get(mTabLayout.getSelectedTabPosition() - 1).deleteFromRealm();
            mRealm.commitTransaction();
            setUpViewPager();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Pair<Fragment, String>> mFragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Pair<Fragment, String>> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position).first;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).second;
        }
    }

//    private Location getMyLocation() {
//        Location currentLocation = null;
//        // Register the listener with the Location Manager to receive location updates
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // 사용자 권한 요청
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
//        }
//        else {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//            // 수동으로 위치 구하기
//            String locationProvider = LocationManager.GPS_PROVIDER;
//            currentLocation = locationManager.getLastKnownLocation(locationProvider);
//            if (currentLocation != null) {
//                double lng = currentLocation.getLongitude();
//                double lat = currentLocation.getLatitude();
//                Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
//            }
//        }
//        return currentLocation;
//    }
//
//    private void settingGPS() {
//        // Acquire a reference to the system Location Manager
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                // TODO 위도, 경도로 하고 싶은 것
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            public void onProviderEnabled(String provider) {
//            }
//
//            public void onProviderDisabled(String provider) {
//            }
//        };
//    }


}
