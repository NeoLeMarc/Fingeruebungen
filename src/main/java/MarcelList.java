import java.util.NoSuchElementException;

/**
 * Created by marcel on 01.12.2016.
 */
public class MarcelList<T> {
    private class Entry<T> {
        private T value;
        private Entry<T> next;
        private Entry<T> previous;
    }

    private Entry<T> firstEntry = null;
    private Entry<T> lastEntry = null;
    int numberOfElements = 0;

    public T get(int pos) {
        if (this.firstEntry == null)
            throw new NoSuchElementException();

        Entry<T> currentEntry = this.firstEntry;

        for (int i = 0; i < pos; i++) {
            currentEntry = currentEntry.next;
            if (currentEntry == null) {
                throw new NoSuchElementException();
            }
        }

        return currentEntry.value;
    }

    public void put(T value) {
        Entry<T> entry = createNewEntry(value);
        checkAndLinkFirstEntry(entry);
        linkWithPreviousEntry(entry);
        this.lastEntry = entry;
    }

    private Entry<T> createNewEntry(T value) {
        Entry<T> entry = new Entry<>();
        entry.value = value;
        entry.previous = this.lastEntry;
        return entry;
    }

    private void checkAndLinkFirstEntry(Entry<T> entry) {
        if (this.firstEntry == null)
            this.firstEntry = entry;
    }

    private void linkWithPreviousEntry(Entry<T> entry) {
        if (this.lastEntry != null) {
            this.lastEntry.next = entry;
            this.lastEntry = entry;
        }
    }
}
