package de.egore911.capacity.persistence.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DbObjectTest {

	@Test
	public void testEquals_newObjects() {
		IntegerDbObject object1 = new IntegerDbObject();
		IntegerDbObject object2 = new IntegerDbObject();

		assertThat(object1, not(equalTo(object2)));
	}

	@Test
	public void testEquals_sameObject() {
		IntegerDbObject object = new IntegerDbObject();

		assertThat(object, equalTo(object));
	}

	@Test
	public void ttestEquals_newObjectsWithSameId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(1);
		IntegerDbObject object2 = new IntegerDbObject();
		object2.setId(1);

		assertThat(object1, equalTo(object2));
	}

	@Test
	public void testEquals_newObjectsWithDifferentId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(0);
		IntegerDbObject object2 = new IntegerDbObject();
		object2.setId(1);

		assertThat(object1, not(equalTo(object2)));
	}

	@Test
	public void testEquals_newObjectsOneWithId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(1);
		IntegerDbObject object2 = new IntegerDbObject();

		assertThat(object1, not(equalTo(object2)));
	}

	@Test
	public void testHashCode_newObjects() {
		IntegerDbObject object1 = new IntegerDbObject();
		IntegerDbObject object2 = new IntegerDbObject();

		assertThat(object1.hashCode(), not(equalTo(object2.hashCode())));
	}

	@Test
	public void testHashCode_sameObject() {
		IntegerDbObject object = new IntegerDbObject();

		assertThat(object.hashCode(), equalTo(object.hashCode()));
	}

	@Test
	public void testHashCode_newObjectsWithSameId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(1);
		IntegerDbObject object2 = new IntegerDbObject();
		object2.setId(1);

		assertThat(object1.hashCode(), equalTo(object2.hashCode()));
	}

	@Test
	public void testHashCode_newObjectsWithDifferentId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(0);
		IntegerDbObject object2 = new IntegerDbObject();
		object2.setId(1);

		assertThat(object1.hashCode(), not(equalTo(object2.hashCode())));
	}

	@Test
	public void testHashCode_newObjectsOneWithId() {
		IntegerDbObject object1 = new IntegerDbObject();
		object1.setId(1);
		IntegerDbObject object2 = new IntegerDbObject();

		assertThat(object1.hashCode(), not(equalTo(object2.hashCode())));
	}

}
