
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.leolin.shortcutbadger.ShortcutBadger;


public class MainActivity extends BaseActivity implements OnClickListener {

    public static final int DRAWER_CATEGORY_IDENTIFIER_START = 1000;
    public static final int DRAWER_STORE_IDENTIFIER = 1110;
    /* Objects related to frame layout */
    public static final int DRAWER_CURRENCY_IDENTIFIER = 1200;

    private static final int INTENT_LOGIN = 1;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    public static Utils sessionObj;
    public static Dialog progress;
    public static JSONArray categoriesJSONObject,childrenCategoryArray, offerCategoryArray,ServicesJSONObject;

    public MainGridViewAdapter myAdapter;
    static int screen_width;
    private static boolean isBottom = true;
    public ImageView productImage;

    public ProgressBar progressBar;


    public TextView productName, newPrice, price,discountPercentage;
    public RatingBar ratingBar;
    public NavigationView navigationView;
    public Menu navigationMenu;
    protected ProgressDialog progressdialog;
    protected SharedPreferences notificationShared;
    protected ActionBarDrawerToggle mDrawerToggle;
    Timer timer, timer1;
    RemindTask swipeAtInterval;
    RemindTask1 swipeAtInterval1;
    boolean firstTime;
    int page = 0;
    ImageView[] dotList;
    ImageView dotImage;
    DetailOnPageChangeListener listener;
    String[] imageUrls; 
    String[] bannerType; 
    String[] nameOfBanner;
    String[] bannerId; 
    List<Element> mainActions;
    HashMap<Integer, List<String>> childActions;
    int counterForChildAction;
    HashMap<String, List<String>> listDataChild;
    List<String> listDataHeader;
    Object response = null, mainResponse = null;
    JSONObject mainObject,BestSellermainObject;
    ProgressBar mainProgressBar;
    JSONArray featuredProductsArr,newArrivalProductsArr, BestSellerJsonArray;
    String mainResponseAsString;
    Editor editor;
    boolean hasbannerActive = true;
    boolean isScroll = true;
    int theme[] = {R.style.AppTheme1, R.style.GrayTheme, R.style.CyanAppTheme, R.style.BrownTheme, R.style.BlueTheme, R.style.PinkTheme, R.style.PurpleTheme};
    int statusBarColor[] = {R.color.dark_primary_color, R.color.colorPrimaryDark_gray, R.color.colorPrimaryDark_cyan,
            R.color.colorPrimaryDark_brown, R.color.colorPrimaryDark_blue, R.color.colorPrimaryDark_pink, R.color.colorPrimaryDark_purple};
    private ViewPager pager;
    private long backPressedTime = 0;
    private LinearLayout featuredProductLayout;
    private LinearLayout latestProductLayout;

    private LinearLayout newarrivalProductLayout,bestseller_products_layout ;

    private DoduaeApplication mDoduaeApplication;
    private Toolbar toolbar;
    private int noOfCategories,noOfOffers;
    private JSONArray languagesJSONObject;
    private JSONArray currenciesJSONObject;
    private int carousalLayoutWidth;
    private int x;
    private int y;
    private TextView model;
    private DataBaseHandler mOfflineDataBaseHandler;
    private CircleImageView mNavHeaderProfileImage;
    private ImageView mNavHeaderMenuSwitcher;
    private RecyclerView mNavDrawerRecyclerView,recyclerView;
    private boolean isGuestMenuVisible;
    private int fromDegrees = 0;
    private int toDegrees = 180;
    private TextView usernameTV;
    private TextView emailTV;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeContainer;
    private View scrollView;
    private Window window;
    private Button search_btn;
    AppBarLayout appBarLayout;
    FrameLayout contantFrame;
    private Tracker mTracker;

    NavDrawerRecyclerViewExpandableAdapter mCrimeExpandableAdapter;
    private int lastExpandedPosition = -1;
    boolean OpenCloseFragment =false ;
    ImageView TopBanner,BottomBanner1a,BottomBanner1b,BottomBanner2a,BottomBanner3a,BottomBanner3b;
    AlertDialog.Builder builder;
    String FBAddtoWishlistEventPrice = null;
    String FBAddtoWishlistEventProcutId = null;
    private String wishLishtProductName = null;
    private boolean discountAvailabilityWishlistProduct = true;


    @Override
    public void onBackPressed() {

        if(appBarLayout != null){
        appBarLayout.setVisibility(View.VISIBLE);
        }
        // search_container.setVisibility(View.GONE);
        //  contantFrame.setVisibility(View.VISIBLE);



        int count=getSupportFragmentManager().getBackStackEntryCount();
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000||count==1) { // 2 secs
            backPressedTime = t;
            getSupportFragmentManager().popBackStack();
            ApplicationExitDialog();
       /*     DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            System.exit(0);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };

            builder = new AlertDialog.Builder(MainActivity.this,R.style.alertDialog);
            builder.setMessage("Are you sure you want to exit?").setNegativeButton(
                    getResources().getString(R.string.cancel),
                    dialogClickListener).setPositiveButton(R.string.exit, dialogClickListener)
                    .setCancelable(true)
                    .show();*/


          //  Toast.makeText(this, getResources().getString(R.string.press_back_to_exit), Toast.LENGTH_SHORT).show();
        } /*else {
            super.onBackPressed();
        }*/
    }



    public void ApplicationExitDialog(){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_exit_dialoge);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button ExitBtn = (Button) dialog.findViewById(R.id.btnYes);

        // TextView contnueShoppingtxt = (TextView)dialog.findViewById(R.id.DialogeText);
        ExitBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
                System.exit(0);

            }
        });

        Button Cancelbtn = (Button) dialog.findViewById(R.id.btnNo);
        Cancelbtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Log.d("Current Locale is" + getClass().getSimpleName(), getResources().getConfiguration().locale.getLanguage());
        super.onCreate(savedInstanceState);
        isOnline();
        setContentView(R.layout.loading_activity);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width = size.x;


        mOfflineDataBaseHandler = new DataBaseHandler(this);

        if(getIntent()!= null){
             Log.d("Read_Uri", getIntent().toString());

            if(getIntent().getData() != null) {
                Uri uri = getIntent().getData();

                try{
                    String referId=uri.getQueryParameter("product_id");
                    String product_name=uri.getQueryParameter("product_title");
                    Intent intent=new Intent(MainActivity.this, ViewProductSimple.class);
                    intent.putExtra("idOfProduct",referId.replace(" ",""));
                    intent.putExtra("nameOfProduct",product_name);
                    startActivity(intent);
                }catch (Exception e) {
                    e.printStackTrace();
                }

            } 

        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setDataFromExtras();


     
        initializeConfiguration();
        loadMainUI();
        
    }




    private void initializeConfiguration() {
        try {
            setContentView(R.layout.activity_main);

            mNavHeaderProfileImage = (CircleImageView) findViewById(R.id.profile_image);
            mNavHeaderMenuSwitcher = (ImageView) findViewById(R.id.nav_menu_switcher);
            View mNavHeaderEmailnMenuSwitcher = findViewById(R.id.nav_header_email_n_menu_switcher);
            mNavHeaderEmailnMenuSwitcher.setOnClickListener(MainActivity.this);
            mNavDrawerRecyclerView = (RecyclerView) findViewById(R.id.nav_drawer_recycler_view);
            mNavDrawerRecyclerView.setNestedScrollingEnabled(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setAutoMeasureEnabled(true);
            mNavDrawerRecyclerView.setLayoutManager(layoutManager);
            navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationMenu = navigationView.getMenu();
            usernameTV = (TextView) findViewById(R.id.username);
            emailTV=(TextView) findViewById(R.id.email);
            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
            scrollView = findViewById(R.id.scrollbar);
            appBarLayout= (AppBarLayout) findViewById(R.id.appbar);

      
            contantFrame=(FrameLayout) findViewById(R.id.content_frame);


            TopBanner = (ImageView) findViewById(R.id.topBanner);
            BottomBanner1a = (ImageView) findViewById(R.id.BottomBanner1a);
            BottomBanner1b= (ImageView) findViewById(R.id.BottomBanner1b);
            BottomBanner2a= (ImageView) findViewById(R.id.BottomBanner2a);
            BottomBanner3a= (ImageView) findViewById(R.id.BottomBanner3a);
            BottomBanner3b= (ImageView) findViewById(R.id.BottomBanner3b);

            TopBanner.setOnClickListener(this);
            BottomBanner1a.setOnClickListener(this);
            BottomBanner1b.setOnClickListener(this);
            BottomBanner2a.setOnClickListener(this);
            BottomBanner3a.setOnClickListener(this);
            BottomBanner3b.setOnClickListener(this);


            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    final int scrollY = scrollView.getScrollY();

                    if (scrollY <=50 ) {
                        swipeContainer.setEnabled(true);
                    } else{
                      // Log.d("LIMIT",String.valueOf(scrollView.getBottom())) ;
                        swipeContainer.setEnabled(false);
                    }
                }
            });


            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(i);
                }
            });

            swipeContainer.setColorSchemeResources(
                    android.R.color.holo_orange_light,
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_red_light,
                    android.R.color.holo_orange_dark
            );

            initNavigationHeader();

            configShared = getSharedPreferences("configureView", MODE_PRIVATE);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

        
            mDrawerToggle = setupDrawerToggle();
            mDrawerToggle.setDrawerIndicatorEnabled(false);


            mDrawerToggle.setToolbarNavigationClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });



            mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
            Display display = getWindowManager().getDefaultDisplay();
            listDataHeader = new ArrayList<>(); //group string.
            listDataChild = new HashMap<>();
            Point size = new Point();
          
            featuredProductLayout = (LinearLayout) findViewById(R.id.featured_Product_Layout);
           
          newarrivalProductLayout = (LinearLayout) findViewById(R.id.newArrival_Product_Layout);


            mainActions = new ArrayList<>();
            childActions = new HashMap<>();


            resetData();
        }
        catch (Exception e) {
            trackException(e, MainActivity.this);
            e.printStackTrace();
        }
    }



    public void BanerImageView(ImageView imageView,String url,int width,int height){

        LoadImagewithPicasso lipObject = LoadImagewithPicasso.getContext(MainActivity.this);

        progressBar = (ProgressBar)findViewById(R.id.progrestBarinImage1);
        lipObject.loadStaticBannersWithPlaceholder(imageView,progressBar,url , width, height);
    }




    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void resetData() {
        try {
            counterForChildAction = 0;
            listDataHeader.clear();
            listDataChild.clear();
            mainActions.clear();
            childActions.clear();
            featuredProductLayout.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("mainResponseAsString")) {
            mainResponseAsString = extras.getString("mainResponseAsString");
            try {
                mainObject = new JSONObject(mainResponseAsString);
                if (mainObject.has("cart")) {
                    Editor editor = getSharedPreferences("customerData", MODE_PRIVATE).edit();
                    editor.putString("cartItems", mainObject.getString("cart"));
                    editor.apply();
                    if (itemCart != null) {
                        SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
                        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
                        Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public void getHomepageResponse(String backresult) {
        try {
            mainObject = new JSONObject(backresult);

            if (mainObject.has("theme_type")) {
                SharedPreferences.Editor editor = getSharedPreferences("theme1", MODE_PRIVATE).edit();
                editor.putInt("theme", theme[mainObject.getInt("theme_type") - 1]);
                editor.apply();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.setStatusBarColor(getResources().getColor(statusBarColor[mainObject.getInt("theme_type") - 1]));
                }
            }
            SharedPreferences themeShared = getSharedPreferences("theme1", MODE_PRIVATE);
            this.setTheme(themeShared.getInt("theme", R.style.AppTheme1));
            if (isInternetAvailable) {
                Log.i("MainActivity", "Inserting In Database");
                mOfflineDataBaseHandler.updateIntoOfflineDB("getHomepage", backresult, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initializeConfiguration();
        loadMainUI();


    }
  

    public void home(View v) {
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void pageSwitcher(int seconds, int length) {
        swipeAtInterval = new RemindTask(length);
        timer = new Timer();
        timer.scheduleAtFixedRate(swipeAtInterval, 0, seconds * 1000);
    }


    public void pageSwitcher1(int seconds, int length) {
        swipeAtInterval1 = new RemindTask1(length);
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(swipeAtInterval1, 0, seconds * 1000);
    }

    public int GetPixelFromDips(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        SharedPreferences shared = getSharedPreferences("customerData", MODE_PRIVATE);
        Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
        String isSeller = (shared.getString("isSeller", ""));
        MenuItem loginMenuItem = menu.findItem(R.id.login);
        MenuItem signupMenuItem = menu.findItem(R.id.signup);
        if (id == R.id.action_settings) {
            if (isLoggedIn) {
               // loginMenuItem.setTitle(getResources().getString(R.string.logout_title));
                menu.findItem(R.id.accountinfo).setVisible(true);
                menu.findItem(R.id.addrbook).setVisible(true);
                menu.findItem(R.id.myorders).setVisible(true);
                menu.findItem(R.id.newslettersubs).setVisible(true);
                menu.findItem(R.id.mywishlist).setVisible(true);
                menu.findItem(R.id.dashboard).setVisible(true);
               // menu.findItem(R.id.logout).setVisible(true);

                loginMenuItem.setVisible(false);
                signupMenuItem.setVisible(false);
                if (isSeller.equalsIgnoreCase("1")) {
                    menu.findItem(R.id.sellerDashboard).setVisible(true);
                    menu.findItem(R.id.sellerOrder).setVisible(true);



                }
            } else {
                loginMenuItem.setTitle(getResources().getString(R.string.login_title));
                signupMenuItem.setVisible(true);
                loginMenuItem.setVisible(true);


                menu.findItem(R.id.accountinfo).setVisible(false);
                menu.findItem(R.id.addrbook).setVisible(false);
                menu.findItem(R.id.myorders).setVisible(false);
                menu.findItem(R.id.newslettersubs).setVisible(false);
                menu.findItem(R.id.mywishlist).setVisible(false);
                menu.findItem(R.id.dashboard).setVisible(false);
              //  menu.findItem(R.id.logout).setVisible(false);

                menu.findItem(R.id.sellerDashboard).setVisible(false);
                menu.findItem(R.id.sellerOrder).setVisible(false);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onLoginClick(View v) {

        startActivityForResult(new Intent(this, LoginRegisterActivity.class), INTENT_LOGIN);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    protected void loadMainUI() {

        try {

            try {
                categoriesJSONObject = mainObject.getJSONArray("categories");
                languagesJSONObject = mainObject.getJSONObject("languages").getJSONArray("languages");
                currenciesJSONObject = mainObject.getJSONObject("currencies").getJSONArray("currencies");
                ServicesJSONObject = mainObject.getJSONArray("service");

            } catch (Exception e) {
                e.printStackTrace();
                trackException(e, MainActivity.this);
            }
            if (!mainObject.getString("banner_status").equalsIgnoreCase("0"))
                loadBannerImages();

        

            if (mainObject.has("cart")) {
                Editor editor = getSharedPreferences("customerData", MODE_PRIVATE).edit();
                try {
                    editor.putString("cartItems", mainObject.getString("cart"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                if (itemCart != null) {
                    SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
                    LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
                    Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
                }
            }


            if(!mainObject.getString("new_arrival_status").equalsIgnoreCase("0")){
            loadNewArrivalProduct();
            } else {
                findViewById(R.id.newArrival_Product_Heading_Layout).setVisibility(View.GONE);
            }



            try {
                if (mainObject.has("banner_heading") && !mainObject.getString("banner_heading").equals("")) {
                    TextView bannersHeading = (TextView) findViewById(R.id.top_categories_text);
                    bannersHeading.setText(mainObject.getString("banner_heading"));
                } else {
                    findViewById(R.id.top_categories_heading_layout).setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                trackException(e, MainActivity.this);
            }






            if(mainObject.getJSONObject("home_banners").length() == 0){
                hasbannerActive = false;
            }else {
                hasbannerActive = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        configShared = getSharedPreferences("configureView", MODE_PRIVATE);
        editor = configShared.edit();
        editor.putBoolean("isMainCreated", true);
        editor.apply();

        int howMany = prefs.getAll().size();
        Log.d("howMany", howMany + "");
        if (mainProgressBar != null)
            mainProgressBar.setVisibility(View.GONE);
        onTrimMemory(TRIM_MEMORY_UI_HIDDEN);



    }





    private void setProducts(final JSONArray productArray, LinearLayout parentView) {
        try {
            int gape = (int) (5 * getResources().getDisplayMetrics().density);
            int padding = (int) (5 * getResources().getDisplayMetrics().density);
            if (productArray.length() != 0) {

                Log.d("NewArrivals",productArray.toString(3));

                for (int i = 0; i < productArray.length(); i++) {

                    final View child = getLayoutInflater().inflate(R.layout.gridhome, null); //child.xml]
                    RelativeLayout productImageLayout = (RelativeLayout) child.findViewById(R.id.productImageLayout);
                    // Gets the layout params that will allow you to resize the layout
                    RelativeLayout.LayoutParams productImageLayoutparams = (RelativeLayout.LayoutParams) productImageLayout.getLayoutParams();
                    // Changes the height and width to the specifi\ed *pixels*
                    productImageLayoutparams.width = (int) (screen_width / 2.3);
                    productImageLayoutparams.height = (int) (screen_width / 2.5);
                    productImageLayout.setLayoutParams(productImageLayoutparams);

                    child.setTag(i);

                    parentView.addView(child);
                    if (i < productArray.length() - 1) {
                        View view = new View(MainActivity.this);
                        view.setLayoutParams(new LinearLayout.LayoutParams(gape, LinearLayout.LayoutParams.MATCH_PARENT));
                        view.setBackgroundColor(Color.TRANSPARENT);
                        parentView.addView(view);
                    }
                    RelativeLayout productInfoLayout = (RelativeLayout) child.findViewById(R.id.relative);
                    productInfoLayout.setPadding(padding, 0, padding, padding);
                    final JSONObject eachProduct = productArray.getJSONObject(i);

                    child.findViewById(R.id.addToCartPop).setTag(i);
                    child.findViewById(R.id.addToCartPop).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final int index = (int) view.getTag();
                            LayoutInflater mInflater = (LayoutInflater) getApplicationContext()
                                    .getSystemService(LAYOUT_INFLATER_SERVICE);
                            View layout = mInflater.inflate(R.layout.popup_menu, null);
                            layout.measure(View.MeasureSpec.UNSPECIFIED,
                                    View.MeasureSpec.UNSPECIFIED);
                            final PopupWindow mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                                    FrameLayout.LayoutParams.WRAP_CONTENT, true);
                            Drawable background = getResources().getDrawable(R.drawable.drawer_shadow);
                            mDropdown.setBackgroundDrawable(background);
                            mDropdown.showAsDropDown(view, 5, 5);
                            //If you want to add any listeners to your textviews, these are two //textviews.
                            final TextView addToCart = (TextView) layout.findViewById(R.id.addToCart);
                            addToCart.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDropdown.dismiss();
                                    progressdialog = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.please_wait), getResources().getString(R.string.processing_request_response), false);
                                    JSONObject jo = new JSONObject();
                                    try {
                                        jo.put("product_id", productArray.getJSONObject(index).getString("product_id"));
                                        jo.put("quantity", "1");
                                        Constants.TEMP_CART_ADD_VALUE  = "1";
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    new DoduaeMakeConnection(MainActivity.this).execute("addToShoppingCart", jo.toString());
                                }
                            });
                            final TextView viewProduct = (TextView) layout.findViewById(R.id.viewProduct);
                            final TextView addToWishList = (TextView) layout.findViewById(R.id.addToWishList);
                            viewProduct.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDropdown.dismiss();
                                    Intent intent = new Intent(MainActivity.this, mDoduaeApplication.viewProductSimple());
                                    try {
                                        intent.putExtra("idOfProduct", productArray.getJSONObject(index).getString("product_id"));
                                        intent.putExtra("nameOfProduct", productArray.getJSONObject(index).getString("name"));

                                    } catch (NumberFormatException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                }
                            });
                            addToWishList.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDropdown.dismiss();
                                    SharedPreferences shared = getSharedPreferences("customerData", MODE_PRIVATE);
                                    Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
                                    if (isLoggedIn) {
                                        progress = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.please_wait), getResources().getString(R.string.processing_request_response), true);
                                        JSONObject jo = new JSONObject();
                                        try {
                                            jo.put("product_id", productArray.getJSONObject(index).getString("product_id"));

                                            FBAddtoWishlistEventPrice = (productArray.getJSONObject(index).getString("special")).replace("AED","");
                                            FBAddtoWishlistEventProcutId = productArray.getJSONObject(index).getString("product_id");
                                            wishLishtProductName = productArray.getJSONObject(index).getString("name");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        new DoduaeMakeConnection(MainActivity.this).execute("addToWishlist", jo.toString());
                                    } else {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this,R.style.alertDialog);
                                        alert.setNegativeButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        alert.setMessage(Html.fromHtml(getResources().getString(R.string.wishlist_msg))).show();
                                    }
                                }
                            });
                            if (eachProduct.has("hasOption"))
                                try {
                                    if ("true".equalsIgnoreCase(eachProduct.getString("hasOption"))) {
                                        addToCart.setVisibility(View.GONE);
                                        viewProduct.setVisibility(View.VISIBLE);
                                    } else {
                                        addToCart.setVisibility(View.VISIBLE);
                                        viewProduct.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }
                    });

                    productImage = (ImageView) child.findViewById(R.id.productImage);
                    progressBar = (ProgressBar)child.findViewById(R.id.progrestBarinImage);

                    String tagString = i + "/" + 0;
                    productImage.setTag(tagString);

                    productImage.setOnClickListener(new OnClickListener() {
                        //
                        @Override
                        public void onClick(View v) {
                            String tagString = (String) v.getTag();
                            String[] indexFlagPair = tagString.split("/");
                            Intent intent = new Intent(MainActivity.this, mDoduaeApplication.viewProductSimple());
                            try {
                                intent.putExtra("idOfProduct", productArray.getJSONObject(Integer.parseInt(indexFlagPair[0])).getString("product_id"));
                                intent.putExtra("nameOfProduct", productArray.getJSONObject(Integer.parseInt(indexFlagPair[0])).getString("name"));
                            } catch (NumberFormatException | JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                    });


                    productName = (TextView) child.findViewById(R.id.productName);
                    productName.setTag(tagString);
                    productName.setOnClickListener(new OnClickListener() {
                        //
                        @Override
                        public void onClick(View v) {
                            String tagString = (String) v.getTag();
                            String[] indexFlagPair = tagString.split("/");
                            Intent intent = new Intent(MainActivity.this, mDoduaeApplication.viewProductSimple());

                            try {
                                intent.putExtra("idOfProduct", productArray.getJSONObject(Integer.parseInt(indexFlagPair[0])).getString("product_id"));
                                intent.putExtra("nameOfProduct", productArray.getJSONObject(Integer.parseInt(indexFlagPair[0])).getString("name"));
                            } catch (NumberFormatException | JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }



                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                        }
                    });
                    if (!BuildConfig.DODUAE_APP) {
                        productName.setMinLines(3);
                        productName.setMaxLines(3);
                    }
                    model = (TextView) child.findViewById(R.id.model);

                    if (eachProduct.has("model"))
                        model.setText(eachProduct.getString("model"));
                    else
                        model.setVisibility(View.GONE);
                    newPrice = (TextView) child.findViewById(R.id.newPrice);
                    price = (TextView) child.findViewById(R.id.price);
                    discountPercentage = (TextView)child.findViewById(R.id.sale);
                    price.setVisibility(View.GONE);
                    ratingBar = (RatingBar) child.findViewById(R.id.ratingBar);

                    newPrice.setSingleLine(false);
                    productName.setText(Html.fromHtml(eachProduct.getString("name")));

                    if (eachProduct.has("discount") && !eachProduct.getString("discount").equalsIgnoreCase("false")){
                        ((TextView) child.findViewById(R.id.sale)).setText(eachProduct.getString("discount")+"\n"+getResources().getString(R.string.off));
                        child.findViewById(R.id.sale).setVisibility(View.VISIBLE);

                    }

                    if (!(eachProduct.getString("special").equalsIgnoreCase("false")) && !(eachProduct.getString("special").equalsIgnoreCase(""))) {
                        newPrice.setText(Html.fromHtml( eachProduct.getString("special")));
                        newPrice.setTextColor(ContextCompat.getColor(this, R.color.special_price_color));
                        price.setText(eachProduct.getString("price"));
                        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        price.setTextSize((float) 11.5);
                        price.setVisibility(View.VISIBLE);
                        discountAvailabilityWishlistProduct = true;

                    } else if (!(eachProduct.getString("price").equalsIgnoreCase("false"))) {
                        newPrice.setText(eachProduct.getString("price"));
                        child.findViewById(R.id.sale).setVisibility(View.GONE);
                        discountAvailabilityWishlistProduct = false;
                    } else {
                        newPrice.setVisibility(View.GONE);
                        child.findViewById(R.id.sale).setVisibility(View.GONE);
                        discountAvailabilityWishlistProduct = false;
                    }
                  //  Log.d("featured_name", eachProduct.getString("name") + "");
                   // Log.d("featured_image", eachProduct.getString("thumb") + "");
//                    Picasso.with(MainActivity.this).load(eachProduct.getString("thumb")).placeholder(R.drawable.placeholder).into(productImage);
                    LoadImagewithPicasso lipObject = LoadImagewithPicasso.getContext(MainActivity.this);
                    if (!eachProduct.getString("thumb").equalsIgnoreCase(""))
                        lipObject.loadImageWithPlaceholder(productImage,progressBar, eachProduct.getString("thumb"), (int) (screen_width / 2.3), (int) (screen_width / 2.3));
                    else
                        lipObject.loadPlaceHolder(productImage, (int) (screen_width / 2.3), (int) (screen_width / 2.3));

                }

            } else {
                findViewById(R.id.featured_product).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private  void loadNewArrivalProduct(){

        try {
            if(mainObject.getJSONObject("modules").has("new_arrival")) {


                newArrivalProductsArr = new JSONArray(mainObject.getJSONObject("modules").getJSONArray("new_arrival").toString());
              //  Log.d("load_NewArrivalProduct", mainObject.getJSONObject("modules").getJSONArray("new_arrival").toString(3) + "");
                newarrivalProductLayout.setTag("newArrivalProducts");

                setProducts(newArrivalProductsArr,newarrivalProductLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void GoToNewArrival(View view){
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        intent.putExtra("ID", "57");
        intent.putExtra("CATEGORY_NAME", "New Arrivals");
        startActivity(intent);

    }




        public JSONArray shuffleBestSellerProducst(JSONArray jArray, int BestSellerShowLimit){

            JSONArray shuffled = new JSONArray();
            ArrayList<Integer> intArr1=new ArrayList<Integer>(jArray.length());
            for(int i = 0; i<jArray.length(); i++){
                intArr1.add(i);
            }
            Collections.shuffle(intArr1);
            for(int i = 0; i < intArr1.size(); i++){
                try {
                    shuffled.put(i, jArray.get(intArr1.get(i)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (i==(BestSellerShowLimit-1))
                    break;
            }
            return shuffled;

}



   

    @Override
    public void onResume() {
        if (itemBell != null) {
            notificationShared = getSharedPreferences("com.android.doduae.notification", MODE_MULTI_PROCESS);
            LayerDrawable icon = (LayerDrawable) itemBell.getIcon();
            HashSet<String> unreadNotifications = (HashSet<String>) notificationShared.getStringSet("unreadNotifications", new HashSet<String>());
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, unreadNotifications.size() + "" + "");

        }

        if (itemCart != null) {
            SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
            LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
        }
        if (mDrawerLayout != null) {
            initNavigationHeader();
        }


        configShared = getSharedPreferences("configureView", MODE_PRIVATE);
        Editor editor = configShared.edit();
        editor.putBoolean("isMainCreated", true);
        editor.apply();
//        updateNavigationHeaderViews();



        // Launcher Badge Value make Zero
        SharedPreferences WebengageNotificationconfigShared = getSharedPreferences("NotificationCounter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = WebengageNotificationconfigShared.edit();
        editor1.putInt("countNotification",0).commit();
        editor1.putInt("unreadServerNotification",0).commit();

        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        //ShortcutBadger.with(getApplicationContext()).remove();  //for 1.1.3

        HomepageViewedEvent();

        super.onResume();
    }


    public void SlideToAbove(View v) {
        final View v1 = v;
        Animation slide;

        featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).setVisibility(View.VISIBLE);
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                5.2f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(400);
        slide.setFillEnabled(true);
        slide.setFillAfter(true);

        featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).startAnimation(slide);
        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).clearAnimation();
                featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).setVisibility(View.VISIBLE);
            }
        });

    }

    public void SlideToDown(View v) {

        final View v1 = v;
        Animation slide;
        featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).setVisibility(View.VISIBLE);
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 5.2f);
        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).clearAnimation();
                featuredProductLayout.findViewWithTag(v1.getTag()).findViewById(R.id.trans).setVisibility(View.GONE);
            }
        });
    }



    private void initNavigationHeader() {
        if (shared != null) {
            if (shared.getBoolean("isLoggedIn", false)) {
                if (shared.contains("customerPic") && !shared.getString("customerPic", "").isEmpty()) {
                    Picasso.get().load(shared.getString("customerPic", "")).into(mNavHeaderProfileImage);
                }
                usernameTV.setText(shared.getString("customerName", ""));
                usernameTV.setClickable(true);
                usernameTV.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoDashboard();
                    }
                });

                emailTV.setText(shared.getString("customerEmail", ""));
                emailTV.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoDashboard();
                    }
                });
                mNavHeaderMenuSwitcher.setVisibility(View.GONE); //Hide By M,  Login user menu indicator and opener
            } else {

                mNavHeaderMenuSwitcher.setVisibility(View.GONE);
                usernameTV.setText(shared.getString("customerName", ""));
                emailTV.setText(getResources().getString(R.string.login_or_register));
            }
        }
    }




    private void StaticBanersClickListener(String type,String link,String title){

            Intent intent = null;

            if (type.equals("product")) {
                intent = new Intent(MainActivity.this, mDoduaeApplication.viewProductSimple());
                intent.putExtra("idOfProduct", link);
                intent.putExtra("nameOfProduct",title);

            } else if (type.equals("category")) {
                SharedPreferences categoryViewShared = getSharedPreferences("categoryView", MODE_PRIVATE);

                if (categoryViewShared.getBoolean("isGridView", false))
                    intent = new Intent(MainActivity.this, mDoduaeApplication.viewCategoryGrid());
                else
                    intent = new Intent(MainActivity.this, mDoduaeApplication.viewCategoryList());


                intent.putExtra("ID",link);
                intent.putExtra("CATEGORY_NAME", title);

            }else {

                if (type.equals("deals")) {
                    SharedPreferences categoryViewShared = getSharedPreferences("SalesCategoryView", MODE_PRIVATE);

                    if (categoryViewShared.getBoolean("isOfferGridView", false))
                        intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());
                    else
                        intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());


                    intent.putExtra("ID", type);
                    intent.putExtra("CATEGORY_NAME", title);

                }
                if (type.equals("promotion")) {
                    SharedPreferences categoryViewShared = getSharedPreferences("SalesCategoryView", MODE_PRIVATE);

                    if (categoryViewShared.getBoolean("isOfferGridView", false))
                        intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());

                    else
                        intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());


                    intent.putExtra("ID", type);
                    intent.putExtra("CATEGORY_NAME", title);

                }

            }

            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



    }






    public class DetailOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            for (int i = 0; i < imageUrls.length; i++) {
                if (i == position)
                    dotList[i].setBackgroundResource(R.drawable.dot_icon_focused);
                else
                    dotList[i].setBackgroundResource(R.drawable.dot_icon_unfocused);
            }
        }

        public int getCurrentPage() {
            return currentPage;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float Offset, int positionOffsetPixels) {
            if (Offset > 0.5f) {
            }
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private String[] images;
        private LayoutInflater inflater;

        ImagePagerAdapter(String[] images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(View view, final int position) {
            final View imageLayout = inflater.inflate(R.layout.item_view_pager_banner, null);
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.progrestBarinImage);




            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (bannerType[position].equals("product")) {
                        intent = new Intent(MainActivity.this, mDoduaeApplication.viewProductSimple());
                        intent.putExtra("idOfProduct", bannerId[position]);
                        intent.putExtra("nameOfProduct", nameOfBanner[position]);

                    }
                    else if (bannerType[position].equals("category")) {
                        SharedPreferences categoryViewShared = getSharedPreferences("categoryView", MODE_PRIVATE);
                        if (categoryViewShared.getBoolean("isGridView", false))
                            intent = new Intent(MainActivity.this, mDoduaeApplication.viewCategoryGrid());
                        else
                            intent = new Intent(MainActivity.this, mDoduaeApplication.viewCategoryList());

                        intent.putExtra("ID", bannerId[position]);
                        intent.putExtra("CATEGORY_NAME", nameOfBanner[position]);
                        intent.putExtra("drawerData", mainObject + "");
                    }else {

                        if (bannerType[position].equals("deals")) {
                            SharedPreferences categoryViewShared = getSharedPreferences("SalesCategoryView", MODE_PRIVATE);

                            if (categoryViewShared.getBoolean("isOfferGridView", false))
                                intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());
                            else
                                intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());

                            intent.putExtra("ID", bannerType[position]);
                            intent.putExtra("CATEGORY_NAME", nameOfBanner[position]);

                        }
                        if (bannerType[position].equals("promotion")) {
                            SharedPreferences categoryViewShared = getSharedPreferences("SalesCategoryView", MODE_PRIVATE);

                            if (categoryViewShared.getBoolean("isOfferGridView", false))
                                intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());
                            else
                                intent = new Intent(MainActivity.this, mDoduaeApplication.viewDailyDealsList());

                            intent.putExtra("ID", bannerType[position]);
                            intent.putExtra("CATEGORY_NAME", nameOfBanner[position]);

                        }


                    }


                    startActivity(intent);
                }
            });
//            Picasso.with(MainActivity.this).load(images[position]).placeholder(R.drawable.placeholder).into(BanerImageView);
            LoadImagewithPicasso lipObject = LoadImagewithPicasso.getContext(MainActivity.this);
            lipObject.loadImageWithPlaceholder(imageView,progressBar, images[position], screen_width, screen_width / 2);
            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }
    }

}
