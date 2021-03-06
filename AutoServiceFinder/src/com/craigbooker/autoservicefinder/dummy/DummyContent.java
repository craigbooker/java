package com.craigbooker.autoservicefinder.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craigbooker.autoservicefinder.dummy.DummyContent.DummyItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		// Add 3 sample items.
		addItem(new DummyItem("1", "Automotive (auto)", "Description 1"));
		addItem(new DummyItem("2", "Auto Detailing (auto_detailing)", "Description 2"));
		addItem(new DummyItem("3", "Auto Glass Services (autoglass)", "Description 3"));
		addItem(new DummyItem("4", "Auto Parts and Supplies (autopartssupplies)", "Description 4"));
		addItem(new DummyItem("5", "Auto Repair (autorepair)", "Description 5"));
		addItem(new DummyItem("6", "Body Shops (bodyshops)", "Description 6"));
		addItem(new DummyItem("7", "Car Dealers (car_dealers)", "Description 7"));
		addItem(new DummyItem("8", "Car Stereo Installation (stereo_installation)", "Description 8"));
		addItem(new DummyItem("9", "Car Wash (carwash)", "Description 9"));
		addItem(new DummyItem("10", "Gas and Service Stations (servicestations)", "Description 10"));
		addItem(new DummyItem("11", "Motorcycle Dealers (motorcycledealers)", "Description 11"));
		addItem(new DummyItem("12", "Motorcycle Repair (motorcyclerepair)", "Description 12"));
		addItem(new DummyItem("13", "Oil Change Stations (oilchange)", "Description 13"));
		addItem(new DummyItem("14", "Parking (parking)", "Description 14"));
		addItem(new DummyItem("15", "RV Dealers (rv_dealers)", "Description 15"));
		addItem(new DummyItem("16", "Smog Check Stations (smog_check_stations)", "Description 16"));
		addItem(new DummyItem("17", "Tires (tires)", "Description 17"));
		addItem(new DummyItem("18", "Towing (towing)", "Description 18"));
		addItem(new DummyItem("19", "Truck Rental (truck_rental)", "Description 19"));
		addItem(new DummyItem("20", "Windshield Installation and Repair (windshieldinstallrepair)", "Description 20"));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;
		public String description;

		public DummyItem(String id, String content, String description) {
			this.id = id;
			this.content = content;
			this.description = description;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
