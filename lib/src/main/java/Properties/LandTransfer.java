
package Properties;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;
 
 
@Contract(
        name = "Properties",
        info = @Info(
                title = "Properties contract",
                description = "A Sample Property transfer chaincode example",
                version = "0.0.1-SNAPSHOT"))
 
 
@Default
public final class LandTransfer implements ContractInterface {
 
	private final Genson genson = new Genson();
	private enum PropertiesErrors {
	        Land_NOT_FOUND,
	        Land_ALREADY_EXISTS
	    }
	
	
	/**
     * Add some initial properties to the ledger
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
    	
        ChaincodeStub stub= ctx.getStub();
        
        Land Land = new Land("1", "Kairav","1000000","Kotagiri","2 acers");
        
        String LandState = genson.serialize(Land);
        
        stub.putStringState("1", LandState);
    }
    
    
    /**
     * Add new Land on the ledger.
     *
     * @param ctx the transaction context
     * @param id the key for the new Land
     * @param model the model of the new Land
     * @param ownername the owner of the new Land
     * @param value the value of the new Land
     * @return the created Land
     */
	
    @Transaction()
    public Land addNewLand(final Context ctx, final String id, final String ownername, final String value, final String address, final String dimension) {
        
    	ChaincodeStub stub = ctx.getStub();
 
        String LandState = stub.getStringState(id);
        
        if (!LandState.isEmpty()) {
            String errorMessage = String.format("Property %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, PropertiesErrors.Land_ALREADY_EXISTS.toString());
        }
        
        Land Land = new Land(id, ownername, value, address, dimension);
        
        LandState = genson.serialize(Land);
        
        stub.putStringState(id, LandState); 
        
        return Land;
    }
 
 
    	/**
	     * Retrieves a Land based upon Land Id from the ledger.
	     *
	     * @param ctx the transaction context
	     * @param id the key
	     * @return the Land found on the ledger if there was one
	     */
    	@Transaction()
	    public Land queryLandById(final Context ctx, final String id) {
	        ChaincodeStub stub = ctx.getStub();
	        String LandState = stub.getStringState(id);
 
	        if (LandState.isEmpty()) {
	            String errorMessage = String.format("Property %s does not exist", id);
	            System.out.println(errorMessage);
	            throw new ChaincodeException(errorMessage, PropertiesErrors.Land_NOT_FOUND.toString());
	        }
	        
	        Land Land = genson.deserialize(LandState, Land.class);
	        return Land;
	    }
    	
        /**
   	     * Changes the owner of a Land on the ledger.
   	     *
   	     * @param ctx the transaction context
   	     * @param id the key
   	     * @param newOwner the new owner
   	     * @return the updated Land
   	     */
   	    @Transaction()
   	    public Land changeLandOwnership(final Context ctx, final String id, final String newLandOwner) {
   	        ChaincodeStub stub = ctx.getStub();
 
   	        String LandState = stub.getStringState(id);
 
   	        if (LandState.isEmpty()) {
   	            String errorMessage = String.format("Property %s does not exist", id);
   	            System.out.println(errorMessage);
   	            throw new ChaincodeException(errorMessage, PropertiesErrors.Land_NOT_FOUND.toString());
   	        }
 
   	        Land Land = genson.deserialize(LandState, Land.class);
 
   	        Land newLand = new Land(Land.getId(), Land.getOwner(), Land.getValue(), Land.getAddress(), Land.getDimension());
   	        
   	        String newLandState = genson.serialize(newLand);
   	        
   	        stub.putStringState(id, newLandState);
 
   	        return newLand;
   	    } 
}


