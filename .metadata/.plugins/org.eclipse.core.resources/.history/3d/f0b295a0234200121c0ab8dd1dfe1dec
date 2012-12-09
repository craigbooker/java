package com.eatrightapp.android;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eatrightapp.android.lazylist.ImageLoader;
import com.eatrightapp.external.era.Dish;
import com.eatrightapp.external.era.ERAService;
import com.eatrightapp.external.era.RestaurantInfo;
import com.eatrightapp.external.yelp.v2.BusinessDetail;
import com.eatrightapp.external.yelp.v2.YelpService;

public class RestaurantActivity extends Activity {

	static final int DIALOG_RATE_DISH = 1;
	static final int DIALOG_FLAG_DISH = 2;
	
	static final int ACTIVITY_CREATE_DISH = 10;
	
	private EatRightApp app;
 
	private ImageLoader imageLoader;
		
	private ScrollView scroller;
	private TextView restaurantNameTV;
	private TextView addressTV;
	private ImageView ratingImageIV;
	private TextView reviewsQtyTV;
	private ImageView restaurantImageIV;
	private TextView snippetTextTV;
	private ImageView snippetImageIV;
	private TextView phoneTV; 
	private ImageButton mapBtn;
	private ImageButton dialBtn;
	private LinearLayout noChainDataExistsPanel;
	private Spinner chainSpinner;
	private LinearLayout chainDataExistsPanel;
	private TextView chainDataWrongLabelTV;
	private TextView chainDataWrongLinkTV;
	private LinearLayout dishesLayout;
	private Dish selectedDish;
	private Button createDishButton;
	private BusinessDetail biz;
	private RestaurantInfo restaurantInfo;
	
	private LinearLayout dishRow(final Dish dish, ViewGroup parent) {
		LayoutInflater myInflater = getLayoutInflater(); 
		View myView = myInflater.inflate(R.layout.dish, parent, false); 
		
		LinearLayout dishLayout;
		TextView dishTitleTV;
		TextView dishDescriptionTV;
		TextView dishNutrientsTV;
		TextView flagLnk;
		ImageView dishImage;
		RatingBar dishRating;
		TextView dishHowManyLikesTV;
		Button rateBtn;

		dishLayout = (LinearLayout) myView.findViewById(R.id.Dish_Layout);
		dishTitleTV = (TextView) myView.findViewById(R.id.Dish_Title);
		dishDescriptionTV = (TextView) myView.findViewById(R.id.Dish_Description);
		dishNutrientsTV = (TextView) myView.findViewById(R.id.Dish_Nutrients);
		flagLnk = (TextView) myView.findViewById(R.id.Dish_FlagLnk);
		dishImage = (ImageView) myView.findViewById(R.id.Dish_Image);
		dishRating = (RatingBar) myView.findViewById(R.id.Dish_RatingBar);
		dishHowManyLikesTV = (TextView) myView.findViewById(R.id.Dish_HowManyLikes);
		rateBtn = (Button) myView.findViewById(R.id.Dish_RateBtn);

		dishTitleTV.setText(dish.getTitle());
		
		flagLnk.setText(Html.fromHtml("<a href=#>Flag</a>"));
		flagLnk.setClickable(true);
		flagLnk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectedDish = dish;
				showDialog(DIALOG_FLAG_DISH);
			}			
		});
		
		dishDescriptionTV.setText(dish.getDescription());
		
		StringBuilder nutrients = new StringBuilder();
		if(dish.getCalories() != null) { 
			nutrients.append(dish.getCalories()).append(" cals ");
		}
		if(dish.getProtein() != null) {
			nutrients.append(dish.getProtein()).append("g protein ");
		}
		if(dish.getFat() != null) {
			nutrients.append(dish.getFat()).append("g fat ");
		}
		if(dish.getCarbs() != null) {
			nutrients.append(dish.getCarbs()).append("g carbs");
		}
		nutrients.append("  ");
		dishNutrientsTV.setText(nutrients.toString());
		
		// TODO: add image
		
		float likes = dish.getLikes() != null ? dish.getLikes().floatValue() : 0.0f;
		float dislikes = dish.getDislikes() != null ? dish.getDislikes().floatValue() : 0.0f;
		float stars = (likes/(likes+dislikes)) * 5.0f;
		if(likes + dislikes > 0) {
			dishRating.setRating(stars);
			float rating = (likes/(likes+dislikes)) * 100.0f;
			//dishHowManyLikesTV.setText(Math.round(rating) + "% recommended");
			dishHowManyLikesTV.setText("  " + dish.getLikes() + " liked, " + dish.getDislikes() + " didn't  ");
		} else {
			dishHowManyLikesTV.setText("Not yet rated.");
		}
		
		
		// TODO: add rate btn
		rateBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectedDish = dish;
				showDialog(DIALOG_RATE_DISH);
			}
		});
		return dishLayout;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (EatRightApp) getApplication();

		imageLoader = app.getImageLoader();
		
		setContentView(R.layout.restaurant);
		
		scroller = (ScrollView) findViewById(R.id.Restaurant_Scroller);
		restaurantNameTV = (TextView) findViewById(R.id.Restaurant_RestaurantName);
		addressTV = (TextView) findViewById(R.id.Restaurant_Address);
		ratingImageIV = (ImageView) findViewById(R.id.Restaurant_RatingImage);
		reviewsQtyTV = (TextView) findViewById(R.id.Restaurant_ReviewsQty);
		restaurantImageIV = (ImageView) findViewById(R.id.Restaurant_Image);
		phoneTV = (TextView) findViewById(R.id.Restaurant_Phone);
		mapBtn = (ImageButton) findViewById(R.id.Restaurant_MapButton);
		dialBtn = (ImageButton) findViewById(R.id.Restaurant_DialButton);
		noChainDataExistsPanel = (LinearLayout) findViewById(R.id.Restaurant_NoChainDataExistsPannel);
		chainSpinner = (Spinner) findViewById(R.id.Restaurant_ChainSpinner);
		chainDataExistsPanel = (LinearLayout) findViewById(R.id.Restaurant_ChainDataExistsPannel);
		chainDataWrongLabelTV = (TextView) findViewById(R.id.Restaurant_ChainDataLabel);
		chainDataWrongLinkTV = (TextView) findViewById(R.id.Restaurant_ChainDataWrongLink);
		dishesLayout = (LinearLayout) findViewById(R.id.Restaurant_DishesLayout);
		createDishButton = (Button) findViewById(R.id.Restaurant_CreateDishBtn);

		// TODO: This may need to be done in an asynctask with a progress dialog.
		String restaurantId = getIntent().getExtras().getString("com.eatrightapp.android.PlacesSearchActivity.YelpId");
		biz = YelpService.findBusiness(restaurantId);
		restaurantInfo = ERAService.findRestaurantInfo(restaurantId);
		List<Dish> dishes = null;
		if(restaurantInfo != null) {
			dishes = ERAService.findDishes(restaurantInfo.isFranchise(), restaurantInfo.isFranchise() ? restaurantInfo.getFranchiseId() : restaurantInfo.getId());
		} else {
			dishes = ERAService.findDishes(false, biz.getId());
		}
		
		restaurantNameTV.setText(biz.getName());
		
		if (biz.getLocation() != null && biz.getLocation().getDisplayAddress() != null) {
			StringBuilder address = new StringBuilder();
			final StringBuilder mapAddress = new StringBuilder();
			for (String addrLine : biz.getLocation().getDisplayAddress()) {
				address.append(addrLine).append("<br />");
				mapAddress.append(addrLine).append(", ");
			}
			addressTV.setText(Html.fromHtml(address.toString()));
//			mapTV.setText(Html.fromHtml(" (<a href=#>Map</a>) "));
//			mapTV.setClickable(true);
			mapBtn.setEnabled(true);
			mapBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(mapAddress.toString())));
						startActivity(intent);
					} catch(ActivityNotFoundException ex) {
						AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantActivity.this);
						builder.setMessage("Unable to launch a map.")
					       .setCancelable(true)
					       .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   RestaurantActivity.this.finish();
					           }
					       });
						builder.show();
					}
				}
				
			});
		} else {
			addressTV.setText("");
			mapBtn.setEnabled(false);
//			mapTV.setText("");
		}

		if(biz.getRatingImgUrl() != null) {
			ratingImageIV.setTag(biz.getRatingImgUrl().toExternalForm());
			imageLoader.DisplayImage(biz.getRatingImgUrl().toExternalForm(), RestaurantActivity.this, ratingImageIV);			
		}

		reviewsQtyTV.setAutoLinkMask(Linkify.WEB_URLS);
		reviewsQtyTV.setText(Html.fromHtml("<a href=" + biz.getMobileUrl() + ">Read " + biz.getReviewCount() + " Yelp user reviews...</a>"));
		final String bizMobileLink = biz.getMobileUrl().toExternalForm();
		reviewsQtyTV.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(bizMobileLink));
				startActivity(intent);				
			}
			
		});
		
		if(biz.getImageUrl() != null) {
			restaurantImageIV.setTag(biz.getImageUrl().toExternalForm());
			imageLoader.DisplayImage(biz.getImageUrl().toExternalForm(), RestaurantActivity.this, restaurantImageIV);			
		}
		
		if(biz.getDisplayPhone() == null) {
			phoneTV.setText("");
		} else {
			phoneTV.setText(biz.getDisplayPhone());
		}
		
		if(biz.getPhone() == null) {
			dialBtn.setEnabled(false);
		} else {
			dialBtn.setEnabled(true);
			final String phone = biz.getPhone();
			dialBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
						startActivity(intent);
					} catch(ActivityNotFoundException ex) {
						AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantActivity.this);
						builder.setMessage("Unable to launch dialer.")
					       .setCancelable(true)
					       .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   RestaurantActivity.this.finish();
					           }
					       });
						builder.show();
					}
				}
				
			});			
		}
		
		if(restaurantInfo == null) {
			noChainDataExistsPanel.setVisibility(LinearLayout.VISIBLE);
			chainDataExistsPanel.setVisibility(LinearLayout.INVISIBLE);
			ArrayAdapter<CharSequence> chainSpinnerOptions = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item); 
			chainSpinnerOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			chainSpinner.setAdapter(chainSpinnerOptions);
			chainSpinnerOptions.add("I don't know");
			chainSpinnerOptions.add("Yes");
			chainSpinnerOptions.add("No");
			chainSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int selectedIndex, long selectedItemId) {
					// TODO Call web service to record value.
					if(selectedIndex > 0) {
						Toast.makeText(getApplicationContext(), "We'll make that change, thanks!", Toast.LENGTH_LONG).show();
						RestaurantInfo restaurantInfo = new RestaurantInfo();
						restaurantInfo.setId(biz.getId());
						restaurantInfo.setFranchise(selectedIndex == 1);
						restaurantInfo.setFranchiseId(biz.getFranchiseId());
						ERAService.updateRestaurantFranchise(restaurantInfo.getId(), restaurantInfo.isFranchise(), restaurantInfo.getFranchiseId());
						startActivity(getIntent()); 
						finish();
					}
				} 

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// Do nothing...
				}
				
			});
		} else {
			noChainDataExistsPanel.setVisibility(LinearLayout.INVISIBLE);
			chainDataExistsPanel.setVisibility(LinearLayout.VISIBLE);
			if(restaurantInfo.isFranchise()) {			
				chainDataWrongLabelTV.setText(Html.fromHtml("This is a <em>chain restaurant</em>.  "));
			} else {		
				chainDataWrongLabelTV.setText(Html.fromHtml("This is not a <em>chain restaurant</em>.  "));
			}
			chainDataWrongLinkTV.setAutoLinkMask(Linkify.ALL);
			chainDataWrongLinkTV.setText(Html.fromHtml("<a href=#>Flag as inaccurate.</a>"));
			chainDataWrongLinkTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Call web service to flag this as inaccurate.			
					Toast.makeText(getApplicationContext(), "Marked for audit, thanks!", Toast.LENGTH_LONG).show(); 
				}
				
			});
		}

		if(dishes != null) {
			for(Dish dish : dishes) {
				dishesLayout.addView(dishRow(dish, dishesLayout));
			}
		}
		
		createDishButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(getClass().getName(), "Starting CreateDishActivity");
				
				String restId = biz.getId();
				String franchId = biz.getFranchiseId();
				boolean franchise = restaurantInfo != null ? restaurantInfo.isFranchise() : false;
								
				Intent createDish = new Intent();
				createDish.setClassName("com.eatrightapp.android", "com.eatrightapp.android.CreateDishActivity");
				createDish.putExtra("com.eatrightapp.android.CreateDishActivity.restaurantId", restId);
				createDish.putExtra("com.eatrightapp.android.CreateDishActivity.franchiseId", franchId);
				createDish.putExtra("com.eatrightapp.android.CreateDishActivity.isFranchise", franchise);
//				startActivity(createDish);
				startActivityForResult(createDish, ACTIVITY_CREATE_DISH);
			}			
		});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == ACTIVITY_CREATE_DISH) {
			startActivity(getIntent()); 
			finish();
        }
    }
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
	    switch(id) {
	    case DIALOG_RATE_DISH: {
			//Context mContext = app.getApplicationContext();
			dialog = new Dialog(this);

			dialog.setContentView(R.layout.rate_dish_dialog);
			dialog.setTitle("Rate This Dish");

			Button saveBtn = (Button) dialog.findViewById(R.id.RateDish_SaveBtn);
			Button cancelBtn = (Button) dialog.findViewById(R.id.RateDish_CancelBtn);
			final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.RateDish_RadioGroup);
			RadioButton likeRdo = (RadioButton) dialog.findViewById(R.id.RateDish_LikeRadio);
			RadioButton dislikeRdo = (RadioButton) dialog.findViewById(R.id.RateDish_DislikeRadio);
						
			saveBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Dish result = ERAService.rateDish(selectedDish.getId(), radioGroup.getCheckedRadioButtonId() == R.id.RateDish_LikeRadio);
					radioGroup.clearCheck();
					dismissDialog(DIALOG_RATE_DISH);
					if(result != null && result.getId() == selectedDish.getId()) {
						Toast.makeText(RestaurantActivity.this, "Rating saved.", Toast.LENGTH_LONG).show();
						startActivity(getIntent()); 
						finish();
					} else {
						Toast.makeText(RestaurantActivity.this, "Error, try again later.", Toast.LENGTH_LONG).show();
					}
				}				
			});
			
			cancelBtn.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					radioGroup.clearCheck();
					dismissDialog(DIALOG_RATE_DISH);
				}
			});
	       break;
	    }
	    case DIALOG_FLAG_DISH: {
			dialog = new Dialog(this);

			dialog.setContentView(R.layout.flag_dish_dialog);
			dialog.setTitle("Flag This Dish");

			Button saveBtn = (Button) dialog.findViewById(R.id.FlagDish_SaveBtn);
			Button cancelBtn = (Button) dialog.findViewById(R.id.FlagDish_CancelBtn);
			final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.FlagDish_RadioGroup);
						
			saveBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ERAService.flagDish(selectedDish.getId(), radioGroup.getCheckedRadioButtonId());
					radioGroup.clearCheck();
					dismissDialog(DIALOG_FLAG_DISH);
//					if(result != null && result.getId() == selectedDish.getId()) {
						Toast.makeText(RestaurantActivity.this, "Dish flagged.", Toast.LENGTH_LONG).show();
//					} else {
//						Toast.makeText(RestaurantActivity.this, "Error, try again later.", Toast.LENGTH_LONG).show();
//					}
				}				
			});
			
			cancelBtn.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					radioGroup.clearCheck();
					dismissDialog(DIALOG_FLAG_DISH);
				}
			});
			break;
	    }
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
}
