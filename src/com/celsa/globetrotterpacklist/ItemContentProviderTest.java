package com.celsa.globetrotterpacklist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import com.celsa.globetrotterpacklist.persistance.ItemContentProvider;

import java.io.ByteArrayOutputStream;

public class ItemContentProviderTest extends ProviderTestCase2<ItemContentProvider> {

    private static MockContentResolver cr;

    public ItemContentProviderTest() {
        super(ItemContentProvider.class, ItemContentProvider.AUTHORITY);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        cr = getMockContentResolver();
    }

    public void testQueryItems() {
        Cursor result = cr.query(ItemContentProvider.ITEMS, new String[] {"_id"}, null, null, null);

        assertEquals(2, result.getCount());
    }

    public void testQueryItem() {
        Cursor result = cr.query(
                ItemContentProvider.ITEMS, null, "_id = ?", new String[] {String.valueOf("1")}, null);

        assertEquals(1, result.getCount());

        result.moveToFirst();
        assertEquals(1, result.getLong(result.getColumnIndex("_id")));
        assertEquals("test", result.getString(result.getColumnIndex("name")));
        assertEquals(0, result.getInt(result.getColumnIndex("status")));
        assertEquals(1, result.getInt(result.getColumnIndex("order")));
    }

    public void testInsertItem() {
        ContentValues cv = new ContentValues();

        cv.put("name", "new");

        Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        cv.put("image", bos.toByteArray());

        cv.put("\"order\"", "99");

        assertNotNull(cr.insert(ItemContentProvider.ITEMS, cv));
    }

    public void testUpdateItem() {
        ContentValues cv = new ContentValues();

        cv.put("name", "test updated");
        cv.put("\"order\"", "99");
        cv.put("status", 1);

        assertEquals(1, cr.update(ItemContentProvider.ITEMS, cv, "_id = ?",
                new String[]{String.valueOf("1")}));
    }

    public void testDeleteItems() {
        assertEquals(2, cr.delete(ItemContentProvider.ITEMS, null, null));

        Cursor result = cr.query(ItemContentProvider.ITEMS, new String[] {"_id"}, null, null, null);
        assertEquals(0, result.getCount());
    }

    public void testDeleteItem() {
        assertEquals(1, cr.delete(ItemContentProvider.ITEMS, "_id = ?",
                new String[]{String.valueOf("1")}));

        Cursor result = cr.query(ItemContentProvider.ITEMS, new String[] {"_id"}, null, null, null);
        assertEquals(1, result.getCount());

        result.moveToFirst();
        assertEquals(2, result.getLong(result.getColumnIndex("_id")));
    }

    public void testGetMaxItemOrder() {
        Cursor result = cr.query(ItemContentProvider.ITEMS,
                new String[] {"max(\"order\")"}, null, null, null);

        try {
            result.moveToFirst();
            assertEquals(2, result.getInt(0));
        } finally {
            result.close();
        }
    }
}