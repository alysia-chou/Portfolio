// == CS400 Fall 2024 File Header Information ==
// Name: Alysia Chou
// Email: wchou22@wisc.edu
// Group: P2.3602
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
	protected LinkedList<Pair>[] table = null;
	private int capacity = 0;

	/**
         * a constructor that takes in the capacity of the array and initialize table
         * 
         * @param capacity
         */
        @SuppressWarnings("unchecked")
        public HashtableMap(int capacity) {

                this.capacity = capacity;
                this.table = (LinkedList<Pair>[]) new LinkedList[capacity];

        }

        /**
         * a constructor that sets capacity of the array to 64 and initialize table
         * 
         * @param capacity
         */
        @SuppressWarnings("unchecked")
        public HashtableMap() {
                this.capacity = 64;
                this.table = (LinkedList<Pair>[]) new LinkedList[capacity];
        }

	protected class Pair {

		public KeyType key;
		public ValueType value;

		public Pair(KeyType key, ValueType value) {
			this.key = key;
			this.value = value;
		}

	}


	/**
	 * Adds a new key,value pair/mapping to this collection.
	 * 
	 * @param key   the key of the key,value pair
	 * @param value the value that key maps to
	 * @throws IllegalArgumentException if key already maps to a value
	 * @throws NullPointerException     if key is null
	 */
	@Override
	public void put(KeyType key, ValueType value) throws IllegalArgumentException {
		// check if key or value is null
		if (key == null || value == null)
			throw new NullPointerException();
		// get the index using hash function
		int index = Math.abs(key.hashCode()) % capacity;
		// if it is null at index initiate a linkedList for it
		if (table[index] == null)
			table[index] = new LinkedList<>();
		// loop through the linkedList at index and check if key is in there
		for (Pair pair : table[index]) {
			if (pair.key.equals(key)) {
				// if it's there throw IllegalArgumentException
				throw new IllegalArgumentException();
			}
		}
		// add the key and value to the linkedList at index
		table[index].add(new Pair(key, value));
		 // check if the table is 80% full
                if ((double) getSize() / this.capacity >= 0.8) 
                        resize();
	}

	/**
	 * A helper method that doubles capacity and rehashing, whenever its load factor
	 * becomes greater than or equal to 80%
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		// double the capacity
		int newCapacity = this.capacity * 2;
		// create a new table
		LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList[newCapacity];
		// loop through the orginal table and add the keys and values to the new table
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				for (Pair pair : table[i]) {
					int index = Math.abs(pair.key.hashCode()) % newCapacity;
					if (newTable[index] == null) {
						newTable[index] = new LinkedList<>();
					}
					newTable[index].add(pair);
				}
			}
		}
		// reassign table to newTable and capacity to newCapacity
		this.table = newTable;
		this.capacity = newCapacity;
	}

	/**
	 * Checks whether a key maps to a value in this collection.
	 * 
	 * @param key the key to check
	 * @return true if the key maps to a value, and false is the key doesn't map to
	 *         a value
	 */
	@Override
	public boolean containsKey(KeyType key) {
		// get the index using hash function
		int index = Math.abs(key.hashCode()) % capacity;
		// if the element of the index is null then key is not in here throw
		// NoSuchElementException
		if (table[index] == null)
			return false;
		// loop through the linkedList at index and check if key is in there and return
		// true
		for (Pair pair : table[index]) {
			if (pair.key.equals(key))
				return true;
		}
		// return false if it's not there
		return false;
	}

	/**
	 * Retrieves the specific value that a key maps to.
	 * 
	 * @param key the key to look up
	 * @return the value that key maps to
	 * @throws NoSuchElementException when key is not stored in this collection
	 */
	@Override
	public ValueType get(KeyType key) throws NoSuchElementException {
		// get the index using hash function
		int index = Math.abs(key.hashCode()) % capacity;
		// if the element of the index is null then there is nothing to get throw
		// NoSuchElementException
		if (table[index] == null)
			throw new NoSuchElementException();
		// loop through the linkedList at index and check if key is in there
		for (Pair pair : table[index]) {
			if (pair.key.equals(key))
				// get the value of key
				return pair.value;
		}
		// if can't find the key throw NoSuchElementException
		throw new NoSuchElementException();
	}

	/**
	 * Remove the mapping for a key from this collection.
	 * 
	 * @param key the key whose mapping to remove
	 * @return the value that the removed key mapped to
	 * @throws NoSuchElementException when key is not stored in this collection
	 */
	@Override
	public ValueType remove(KeyType key) throws NoSuchElementException {
		// get the index using hash function
		int index = Math.abs(key.hashCode()) % capacity;
		// if the element of the index is null then there is nothing to remove throw
		// NoSuchElementException
		if (table[index] == null)
			throw new NoSuchElementException();
		// loop through the linkedList at index and check if key is in there
		for (Pair pair : table[index]) {
			if (pair.key.equals(key)) {
				// create an field to store the value that is going to be removed
				ValueType value = pair.value;
				// remove the key and value
				table[index].remove(pair);
				return value;
			}
		}
		// if can't find the key throw NoSuchElementException
		throw new NoSuchElementException();
	}

	/**
	 * Removes all key,value pairs from this collection.
	 */
	@Override
	public void clear() {
		// loop through all the array and set very index to null
		for (int i = 0; i < capacity; i++)
			table[i] = null;

	}

	/**
	 * Retrieves the number of keys stored in this collection.
	 * 
	 * @return the number of keys stored in this collection
	 */
	@Override
	public int getSize() {
		// initialize size to keep track of number of elements in table
		int size = 0;
		// loop through the array and add the size of each linkedList to size
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null)
				size += table[i].size();
		}
		return size;
	}

	/**
	 * Retrieves this collection's capacity.
	 * 
	 * @return the size of te underlying array for this collection
	 */
	@Override
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Retrieves this collection's keys.
	 * 
	 *  @return a list of keys in the underlying array for this collection 
	 */
	public List<KeyType> getKeys() {
		List<KeyType> list = new LinkedList<KeyType>();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				for (Pair pair : table[i])
					list.add(pair.key);
			}
		}
		return list;
	} 



	/**
	 * test the first constructors, put(), getSize(), and getCapacity()
	 * 
	 * @return true if all tests pass, and false otherwise
	 */
	@Test
	public void test1() {
		// initialize the HashtableMap with key being String and value being Integer
		HashtableMap<String, Integer> table = new HashtableMap<String, Integer>();
		// put keys and value in table
		table.put("A", 0);
		table.put("B", 1);
		table.put("C", 2);
		table.put("D", 3);

		// check if the methods work as expected
		if (table.getSize() != 4)
			Assertions.fail();
		if (table.getCapacity() != 64)
			Assertions.fail();

	}

	/**
	 * test the second constructor and containsKey()
	 * 
	 * @return true if all tests pass, and false otherwise
	 */
	@Test
	public void test2() {
		// initialize the HashtableMap with key being String and value being Integer
		HashtableMap<String, Integer> table = new HashtableMap<String, Integer>(10);
		// put keys and value in table
		table.put("A", 0);
		table.put("B", 1);
		table.put("C", 2);
		table.put("D", 3);

		// check if the methods work as expected
		if (table.getCapacity() != 10)
			Assertions.fail();
		if (table.containsKey("A") != true)
			Assertions.fail();
		if (table.containsKey("F") != false)
			Assertions.fail();
	}

	/**
	 * test get() with valid and invalid inputs
	 * 
	 * @return true if all tests pass, and false otherwise
	 */
	@Test
	public void  test3() {
		// initialize the HashtableMap with key being String and value being Integer
		HashtableMap<String, Integer> table = new HashtableMap<String, Integer>();
		// put keys and value in table
		table.put("A", 0);
		table.put("B", 1);
		table.put("C", 2);
		table.put("D", 3);

		// check if the methods work as expected
		try {
			if (table.get("B") != 1)
				Assertions.fail();
			table.get("K");
			Assertions.fail();
		} catch (NoSuchElementException e) {
		}
		
	}

	/**
	 * test remove() with valid and invalid inputs
	 * 
	 * @return true if all tests pass, and false otherwise
	 */
	@Test
	public void test4() {
		// initialize the HashtableMap with key being String and value being Integer
		HashtableMap<String, Integer> table = new HashtableMap<String, Integer>();
		// put keys and value in table
		table.put("A", 0);
		table.put("B", 1);
		table.put("C", 2);
		table.put("D", 3);

		// check if the methods work as expected
		try {
			if (table.remove("B") != 1)
				Assertions.fail();
			if (table.getSize() != 3) 
				Assertions.fail();
			table.remove("K");
			Assertions.fail();
		} catch (NoSuchElementException e) {
		}
	}

	/**
	 * test put() with invalid values and clear()
	 * 
	 * @return
	 */
	@Test
	public void test5() {
		// initialize the HashtableMap with key being String and value being Integer
		HashtableMap<String, Integer> table = new HashtableMap<String, Integer>();

		// check if the methods work as expected
		try {
			// put keys and value in table
			table.put("A", 0);
			table.put("B", 1);
			table.put("A", 2);
			Assertions.fail();			
		} catch (IllegalArgumentException e) {
		}

		try {
			// put keys and value in table
			table.put("C", 3);
			table.put("D", 4);
			table.put("E", 5);
			table.put(null, null);
			Assertions.fail();
		} catch (NullPointerException e) {
		}
		table.clear();
		if (table.getSize() != 0)
			Assertions.fail();
	}
}
