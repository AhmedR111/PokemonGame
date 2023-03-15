package util;

import model.Item;
import model.ItemType;

public class ItemFactory {

    /**
     * Factory method for creating a new Item instance according to a type
     * @param itemType
     * @return
     */
    public static Item createItem(ItemType itemType) {

        Item result = null;
        switch (itemType) {
            case SCUT:
                result = new Item(null, null, null, 2, 2);
                break;
            case VESTA:
                result = new Item(10, null, null, null, null);
                break;

            case SABIUTA:
                result = new Item(null, 3, null, null, null);
                break;

            case BAGHETA_MAGICA:
                result = new Item(null, null, 3, null, null);
                break;

            case VITAMINE:
                result = new Item(2, 2, 2, null, null);
                break;

            case BRAD_DE_CRACIUN:
                result = new Item(null, 3, null, 1, null);
                break;

            case PELERINA:
                result = new Item(null, null, null, null, 3);
                break;

            default:
                throw new UnsupportedOperationException("NO ITEM FOUND");

        }

        return result;
    }

}
