package com.mikepenz.materialdrawer.app;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;
import com.mikepenz.iconics.utils.IconicsConvertersKt;
import com.mikepenz.iconics.utils.IconicsDrawableExtensionsKt;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.iconics.IconicsImageHolder;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.MaterialDrawerSliderViewExtensionsKt;
import com.mikepenz.materialdrawer.widget.AccountHeaderView;
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class DrawerActivityJava extends AppCompatActivity
{
	private static final int PROFILE_SETTING = 100000;

	//save our header or result
	private AccountHeaderView headerResult = null;
	private ActionBarDrawerToggle actionBarDrawerToggle = null;
	private MaterialDrawerSliderView slider = null;
	private DrawerLayout root = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);

		//Remove line to test RTL support
		//getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

		// Handle Toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);

		root = findViewById(R.id.root);
		slider = findViewById(R.id.slider);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, root, toolbar, com.mikepenz.materialdrawer.R.string.material_drawer_open, com.mikepenz.materialdrawer.R.string.material_drawer_close);
		root.addDrawerListener(actionBarDrawerToggle);

		// Create a few sample profile
		// NOTE you have to define the loader logic too. See the CustomApplication for more details
		final IProfile profile1 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Mike Penz"));
			setDescription(new StringHolder("mikepenz@gmail.com"));
			setIcon(new ImageHolder("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"));
			setIdentifier(100);
		}};
		final IProfile profile2 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Demo User"));
			setDescription(new StringHolder("demo@github.com"));
			setIcon(new ImageHolder("https://avatars2.githubusercontent.com/u/3597376?v=3&s=460"));
			setIdentifier(101);
		}};
		final IProfile profile3 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Max Muster"));
			setDescription(new StringHolder("max.mustermann@gmail.com"));
			setIcon(new ImageHolder(R.drawable.profile2));
			setIdentifier(102);
		}};
		final IProfile profile4 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Felix House"));
			setDescription(new StringHolder("felix.house@gmail.com"));
			setIcon(new ImageHolder(R.drawable.profile3));
			setIdentifier(103);
		}};
		final IProfile profile5 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Mr. X"));
			setDescription(new StringHolder("mister.x.super@gmail.com"));
			setIcon(new ImageHolder(R.drawable.profile4));
			setIdentifier(104);
		}};
		final IProfile profile6 = new ProfileDrawerItem()
		{{
			setName(new StringHolder("Batman"));
			setDescription(new StringHolder("batman@gmail.com"));
			setIcon(new ImageHolder(R.drawable.profile5));
			setIdentifier(105);
			setBadge(new StringHolder("123"));
			setBadgeStyle(new BadgeStyle()
			{{
				setTextColor(ColorHolder.Companion.fromColor(Color.BLACK));
				setColor(ColorHolder.Companion.fromColor(Color.WHITE));
			}});
		}};


		// Create the AccountHeader
		headerResult = new AccountHeaderView(this)
		{
			{
				addProfiles(
						profile1,
						profile2,
						profile3,
						profile4,
						profile5,
						profile6,
						//don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
						new ProfileSettingDrawerItem()
						{
							{
								setName(new StringHolder("Add Account"));
								setDescription(new StringHolder("Add new GitHub Account"));
//								IconicsDrawable iconicsDrawable = new IconicsDrawable(DrawerActivity2.this, GoogleMaterial.Icon.gmd_add);
//								IconicsDrawableExtensionsKt.actionBar(iconicsDrawable);
//								IconicsConvertersKt.setPaddingDp(iconicsDrawable, 5);
								setIcon(new ImageHolder(new IconicsDrawable(DrawerActivityJava.this, GoogleMaterial.Icon.gmd_add)
								{
									{
										IconicsDrawableExtensionsKt.actionBar(this);
										IconicsConvertersKt.setPaddingDp(this, 5);
									}
								}));
								setIconTinted(true);
								setIdentifier(PROFILE_SETTING);
							}
						},
						new ProfileSettingDrawerItem()
						{
							{
								setName(new StringHolder("Manage Account"));
								setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_settings));
								setIdentifier(100001);
							}
						}
				);
				attachToSliderView(slider);
				setOnAccountHeaderListener((view, profile, current) ->
				{
					//sample usage of the onProfileChanged listener
					//if the clicked item has the identifier 1 add a new profile ;)
					if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING)
					{
						int count = 100 + headerResult.getProfiles().size() + 1;
						IProfile newProfile = new ProfileDrawerItem()
						{{
							setName(new StringHolder("Batman" + count));
							setDescription(new StringHolder("batman" + count + "@gmail.com"));
							setIcon(new ImageHolder(R.drawable.profile5));
							setIdentifier(count);
						}}.withNameShown(true);
						if (headerResult.getProfiles() != null)
						{
							//we know that there are 2 setting elements. set the new profile above them ;)
							headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
						}
						else
						{
							headerResult.addProfiles(newProfile);
						}
					}

					//false if you have not consumed the event and it should close the drawer
					return false;
				});
				withSavedInstance(savedInstanceState);
			}
		};

		MaterialDrawerSliderViewExtensionsKt.addItems(slider,
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_compact_header));
						setDescription(new StringHolder(R.string.drawer_item_compact_header_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_brightness_5));
						setSelectable(false);
						setIdentifier(1);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_action_bar_drawer));
						setDescription(new StringHolder(R.string.drawer_item_action_bar_drawer_desc));
						setIcon(new IconicsImageHolder(FontAwesome.Icon.faw_home));
						setSelectable(false);
						setIdentifier(2);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_multi_drawer));
						setDescription(new StringHolder(R.string.drawer_item_multi_drawer_desc));
						setIcon(new IconicsImageHolder(FontAwesome.Icon.faw_gamepad));
						setSelectable(false);
						setIdentifier(3);
					}
				},
//				new PrimaryDrawerItem()
//				{
//					{
//						setName(new StringHolder(R.string.drawer_item_non_translucent_status_drawer));
//						setDescription(new StringHolder(R.string.drawer_item_non_translucent_status_drawer_desc));
//						setIcon(new IconicsImageHolder(FontAwesome.Icon.faw_eye));
//						setSelectable(false);
//						setIdentifier(4);
//						setBadgeStyle(new BadgeStyle()
//						{
//							{
//								setTextColor(ColorHolder.Companion.fromColor(Color.WHITE));
//								setColor(ColorHolder.Companion.fromColor(R.color.md_red_700));
//							}
//						});
//					}
//				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_advanced_drawer));
						setDescription(new StringHolder(R.string.drawer_item_advanced_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_adb));
						setSelectable(false);
						setIdentifier(5);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_embedded_drawer));
						setDescription(new StringHolder(R.string.drawer_item_embedded_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_battery_full));
						setSelectable(false);
						setIdentifier(7);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_fullscreen_drawer));
						setDescription(new StringHolder(R.string.drawer_item_fullscreen_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_label));
						setSelectable(false);
						setIdentifier(8);
					}
				},
//				new PrimaryDrawerItem()
//				{
//					{
//						setName(new StringHolder(R.string.drawer_item_custom_container_drawer));
//						setDescription(new StringHolder(R.string.drawer_item_custom_container_drawer_desc));
//						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_my_location));
//						setSelectable(false);
//						setIdentifier(9);
//					}
//				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_menu_drawer));
						setDescription(new StringHolder(R.string.drawer_item_menu_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_filter_list));
						setSelectable(false);
						setIdentifier(10);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_mini_drawer));
						setDescription(new StringHolder(R.string.drawer_item_mini_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_battery_charging_full));
						setSelectable(false);
						setIdentifier(11);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_fragment_drawer));
						setDescription(new StringHolder(R.string.drawer_item_fragment_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_disc_full));
						setSelectable(false);
						setIdentifier(12);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_collapsing_toolbar_drawer));
						setDescription(new StringHolder(R.string.drawer_item_collapsing_toolbar_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_camera_rear));
						setSelectable(false);
						setIdentifier(13);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_persistent_compact_header));
						setDescription(new StringHolder(R.string.drawer_item_persistent_compact_header_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_brightness_5));
						setSelectable(false);
						setIdentifier(14);
					}
				},
				new PrimaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_crossfade_drawer_layout_drawer));
						setDescription(new StringHolder(R.string.drawer_item_crossfade_drawer_layout_drawer_desc));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_format_bold));
						setSelectable(false);
						setIdentifier(15);
					}
				},
				new ExpandableBadgeDrawerItem()
				{
					{
						setName(new StringHolder("Collapsable Badge"));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_format_bold));
						setSelectable(false);
						setIdentifier(18);
						setBadgeStyle(new BadgeStyle()
						{
							{
								setTextColor(ColorHolder.Companion.fromColor(Color.WHITE));
								setColor(ColorHolder.Companion.fromColor(R.color.md_red_700));
							}
						});
						setBadge(new StringHolder("100"));
						setSubItems(
								new SecondaryDrawerItem()
								{
									{
										setName(new StringHolder("CollapsableItem 1"));
										setLevel(2);
										setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_format_bold));
										setIdentifier(2000);
									}
								},
								new SecondaryDrawerItem()
								{
									{
										setName(new StringHolder("CollapsableItem 2"));
										setLevel(2);
										setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_format_bold));
										setIdentifier(2001);
									}
								}
						);
					}
				},
				new ExpandableDrawerItem()
				{
					{
						setName(new StringHolder("Collapsable Badge"));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_filter_list));
						setSelectable(false);
						setIdentifier(18);
						setSubItems(
								new SecondaryDrawerItem()
								{
									{
										setName(new StringHolder("CollapsableItem 1"));
										setLevel(2);
										setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_filter_list));
										setIdentifier(2002);
									}
								},
								new SecondaryDrawerItem()
								{
									{
										setName(new StringHolder("CollapsableItem 2"));
										setLevel(2);
										setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_filter_list));
										setIdentifier(2003);
									}
								}
						);
					}
				},
				new SectionDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_section_header));
					}
				},
				new SecondaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_open_source));
						setIcon(new IconicsImageHolder(FontAwesome.Icon.faw_github));
						setSelectable(false);
						setIdentifier(20);
					}
				},
				new SecondaryDrawerItem()
				{
					{
						setName(new StringHolder(R.string.drawer_item_contact));
						setIcon(new IconicsImageHolder(GoogleMaterial.Icon.gmd_format_color_fill));
						setIdentifier(21);
						setTag("Bullhorn");
					}
				}
		);

		slider.setOnDrawerItemClickListener((view, drawerItem, position) ->
		{
			//check if the drawerItem is set.
			//there are different reasons for the drawerItem to be null
			//--> click on the header
			//--> click on the footer
			//those items don't contain a drawerItem

			if (drawerItem != null)
			{
				Intent intent = null;
				if (drawerItem.getIdentifier() == 1)
				{
					intent = new Intent(DrawerActivityJava.this, CompactHeaderDrawerActivity.class);
				}
				else if (drawerItem.getIdentifier() == 2)
				{
					intent = new Intent(DrawerActivityJava.this, ActionBarActivity.class);
				}
				else if (drawerItem.getIdentifier() == 3)
				{
					intent = new Intent(DrawerActivityJava.this, MultiDrawerActivity.class);
				}
//				else if (drawerItem.getIdentifier() == 4)
//				{
//					intent = new Intent(DrawerActivity2.this, NonTranslucentDrawerActivity.class);
//				}
				else if (drawerItem.getIdentifier() == 5)
				{
					intent = new Intent(DrawerActivityJava.this, AdvancedActivity.class);
				}
				else if (drawerItem.getIdentifier() == 7)
				{
					intent = new Intent(DrawerActivityJava.this, EmbeddedDrawerActivity.class);
				}
				else if (drawerItem.getIdentifier() == 8)
				{
					intent = new Intent(DrawerActivityJava.this, FullscreenDrawerActivity.class);
				}
//				else if (drawerItem.getIdentifier() == 9)
//				{
//					intent = new Intent(DrawerActivity2.this, CustomContainerActivity.class);
//				}
				else if (drawerItem.getIdentifier() == 10)
				{
					intent = new Intent(DrawerActivityJava.this, MenuDrawerActivity.class);
				}
				else if (drawerItem.getIdentifier() == 11)
				{
					intent = new Intent(DrawerActivityJava.this, MiniDrawerActivity.class);
				}
				else if (drawerItem.getIdentifier() == 12)
				{
					intent = new Intent(DrawerActivityJava.this, FragmentActivity.class);
				}
				else if (drawerItem.getIdentifier() == 13)
				{
					intent = new Intent(DrawerActivityJava.this, CollapsingToolbarActivity.class);
				}
				else if (drawerItem.getIdentifier() == 14)
				{
					intent = new Intent(DrawerActivityJava.this, PersistentDrawerActivity.class);
				}
				else if (drawerItem.getIdentifier() == 15)
				{
					intent = new Intent(DrawerActivityJava.this, CrossfadeDrawerLayoutActvitiy.class);
				}
				else if (drawerItem.getIdentifier() == 20)
				{
					intent = new LibsBuilder()
							.withFields(R.string.class.getFields())
							.withLicenseShown(true)
							.intent(DrawerActivityJava.this);
				}
				if (intent != null)
				{
					DrawerActivityJava.this.startActivity(intent);
				}
			}

			return false;
		});
		slider.setSavedInstance(savedInstanceState);


		//only set the active selection or active profile if we do not recreate the activity
		if (savedInstanceState == null)
		{
			// set the selection to the item with the identifier 11
			slider.setSelection(21, false);

			//set the active profile
			headerResult.setActiveProfile(profile3);
		}

		MaterialDrawerSliderViewExtensionsKt.updateBadge(slider, 4, new StringHolder(10 + ""));
	}

    /*
    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };
    */

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		actionBarDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		//add the values which need to be saved from the drawer to the bundle
		outState = slider.saveInstanceState(outState);
		//add the values which need to be saved from the accountHeader to the bundle
		outState = headerResult.saveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onBackPressed()
	{
		//handle the back press :D close the drawer first and if the drawer is closed close the activity
		if (root.isDrawerOpen(slider))
		{
			root.closeDrawer(slider);
		}
		else
		{
			super.onBackPressed();
		}
	}

}