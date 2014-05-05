package com.celsa.globetrotterpacklist;

import android.test.ActivityInstrumentationTestCase2;
import com.celsa.globetrotterpacklist.entities.Item;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.celsa.globetrotterpacklist.ItemsTest \
 * com.celsa.globetrotterpacklist.tests/android.test.InstrumentationTestRunner
 */
public class ItemsTest extends ActivityInstrumentationTestCase2<Items> {

    private Items items;
    private ItemService itemService;

    public ItemsTest() {
        super(Items.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        items = getActivity();
        itemService = getActivity().getItemService();

    }

    public void testService() throws Exception {
        assertNotNull(itemService);
    }

    public void testItemServiceGetItem() throws Exception {
        Item item = itemService.getItem(1);

        assertEquals(1, item.getId());
        assertEquals("test", item.getName());
    }
}
