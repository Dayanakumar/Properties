package Properties;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.Objects;

 
@DataType()
public final class Land {
 
	@Property()
	private final String id;
 
 
	@Property()
	private final String owner;
 
	@Property()
	private final String value;
	
	@Property()
	private final String address;
	
	@Property()
	private final String dimension;
 
	public String getId() {
		return id;
	}
 
	public String getOwner() {
		return owner;
	}
 
	public String getValue() {
		return value;
	}
	public String getAddress() {
		return address;
	}
	public String getDimension() {
		return dimension;
	}
 
	public Land(@JsonProperty("id") final String id, @JsonProperty("owner") final String owner,
			@JsonProperty("value") final String value ,@JsonProperty("address") final String address, @JsonProperty("dimension") final String dimension ) {
		this.id = id;
		this.owner= owner;
		this.value = value;
		this.address = address;
		this.dimension = dimension;
	}
 
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
 
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
 
		Land other = (Land) obj;
 
		return Objects.deepEquals(new String[] { getId(), getOwner(), getValue(),getAddress()},
				new String[] { other.getId(), other.getOwner(), other.getValue(), other.getAddress(), other.getDimension()});
	}
 
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getOwner(), getValue(), getAddress(), getDimension());
	}
 
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id + ", owner=" + owner + ", value=" + value + ", address=" + address+", dimension=" + dimension+"]";
	}
 
}


