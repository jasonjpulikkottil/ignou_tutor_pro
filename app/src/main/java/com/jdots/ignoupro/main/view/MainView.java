package com.jdots.ignoupro.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.Utils;
import com.jdots.ignoupro.user.data_model.User;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created for chatting on 16/08/16.
 */

public class MainView extends CoordinatorLayout implements MainDisplayer {

    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavigationView logoutView;
    private CircleImageView profileImageView;
    private TextView nameTextView;
    private TextView emailTextView;

    private DrawerActionListener drawerActionListener;
    private final NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            drawerActionListener.onNavigationItemSelected(item);
            return true;
        }
    };
    private final OnClickListener headerClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            drawerActionListener.onHeaderSelected();
        }
    };
    private NavigationActionListener navigationActionListener;
    private final OnClickListener navigationClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            navigationActionListener.onHamburgerPressed();
        }
    };
    private SearchActionListener searchActionListener;
    private final MaterialSearchView.OnQueryTextListener queryTextListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            searchActionListener.showFilteredUsers(newText);
            return false;
        }
    };

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_main_view, this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_hamburger);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        logoutView = (NavigationView) navigationView.findViewById(R.id.logout_view);
        View headerLayout = navigationView.getHeaderView(0);
        profileImageView = (CircleImageView) headerLayout.findViewById(R.id.profileImageView);
        nameTextView = (TextView) headerLayout.findViewById(R.id.nameTextView);
        emailTextView = (TextView) headerLayout.findViewById(R.id.emailTextView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
    }

    @Override
    public void attach(final DrawerActionListener drawerActionListener, NavigationActionListener navigationActionListener, SearchActionListener searchActionListener) {

        this.drawerActionListener = drawerActionListener;
        this.navigationActionListener = navigationActionListener;
        this.searchActionListener = searchActionListener;

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        logoutView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        profileImageView.setOnClickListener(headerClickListener);
        nameTextView.setOnClickListener(headerClickListener);
        emailTextView.setOnClickListener(headerClickListener);
        toolbar.setNavigationOnClickListener(navigationClickListener);
    }

    @Override
    public void detach(DrawerActionListener drawerActionListener, NavigationActionListener navigationActionListener, SearchActionListener searchActionListener) {
        //drawer.removeDrawerListener();
        navigationView.setNavigationItemSelectedListener(null);
        logoutView.setNavigationItemSelectedListener(null);
        profileImageView.setOnClickListener(null);
        nameTextView.setOnClickListener(null);
        emailTextView.setOnClickListener(null);
        toolbar.setNavigationOnClickListener(null);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setUser(User user) {
        Utils.loadImageElseWhite(user.getImage(),profileImageView,getContext());
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
    }

    @Override
    public void inflateMenu() {
        if (!toolbar.getMenu().hasVisibleItems()) {
            toolbar.inflateMenu(R.menu.fragment_users_itemlist);
            searchView.setOnQueryTextListener(queryTextListener);

            MenuItem item = toolbar.getMenu().findItem(R.id.search_user);
            searchView.setMenuItem(item);
        }
    }

    @Override
    public void clearMenu() {
        toolbar.getMenu().clear();
    }

    @Override
    public boolean onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return true;
        }
        return false;
    }

    @Override
    public void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

}

