package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void test_sellIn() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Dagger", 5, 5));
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int sellIn = items.get(0).getSellIn();

		assertEquals("Failed sellIn for Dagger", 4, sellIn);
	}
	
	@Test
	public void test_qualityAfterExpiration() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Dagger", 1, 30));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		assertEquals("Failed quality for Dagger", 25, quality);
	}
	
	@Test
	public void test_qualityNotNegative() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Dagger", 1, 1));
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		assertEquals("Failed quality for Dagger", 0, quality);
	}
	
	@Test
	public void test_BrieAging() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 3, 10));
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Brie", 12, quality);
	}
	
	@Test
	public void test_BrieAgingOver50() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 1, 47));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Brie", 50, quality);
	}
	
	@Test
	public void test_BackstageAging() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 1));
		inn.oneDay(); // 2
		inn.oneDay(); // 4
		inn.oneDay(); // 6
		inn.oneDay(); // 8
		inn.oneDay(); // 10
		inn.oneDay(); // 12
		inn.oneDay(); // 15
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Backstage pass", 15, quality);
	}
	
	@Test
	public void test_BackstageQualityAtZero() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 2, 10));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Backstage pass", 0, quality);
	}
	
	@Test
	public void test_SulfurasQuality() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 4, 80));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Sulfuras", 80, quality);
	}
	
	@Test
	public void test_SulfurasAging() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 4, 80));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Sulfuras", 4, sellIn);
	}
	
	@Test
	public void test_UpdateQualityLoops() {
		GildedRose inn = new GildedRose();
		inn.oneDay(); // 0 loops
		
		assertEquals("Failed at 0 loops", new ArrayList<Item>(), inn.getItems());
		
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.setItem(new Item("Aged Brie", 2, 0));
		inn.oneDay(); // 2 loops
		
		List<Item> items = inn.getItems();
		
		assertEquals("Failed at 2 loops", 9, items.get(0).getSellIn());
		assertEquals("Failed at 2 loops", 19, items.get(0).getQuality());
		assertEquals("Failed at 2 loops", 1, items.get(1).getSellIn());
		assertEquals("Failed at 2 loops", 1, items.get(1).getQuality());
		
		inn.setItem(new Item("Elixir of the Mongoose", 5, 7));
		inn.oneDay(); // 3 loops
		
		assertEquals("Failed at 3 loops", 8, items.get(0).getSellIn());
		assertEquals("Failed at 3 loops", 18, items.get(0).getQuality());
		assertEquals("Failed at 3 loops", 0, items.get(1).getSellIn());
		assertEquals("Failed at 3 loops", 2, items.get(1).getQuality());
		assertEquals("Failed at 3 loops", 4, items.get(2).getSellIn());
		assertEquals("Failed at 3 loops", 6, items.get(2).getQuality());
	}
}
