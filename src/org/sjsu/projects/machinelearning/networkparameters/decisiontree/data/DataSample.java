package decisiontree.data;

import java.util.Optional;

import decisiontree.feature.Feature;
import decisiontree.label.Label;

/**
 * Labeled training data sample.
 * 
 * @author Keyur Golani
 * @author Kalgi Bhatt
 *
 */
public interface DataSample {
    
    /**
     * Get sample data value from specified column.
     * 
     * @return Data value.
     */
    Optional<Object> getValue(String column);
    
    /**
     * Assigned label of training data.
     * 
     * @return Label.
     */
    Label getLabel();
    
    /**
     * Syntactic sugar to check if data has feature.
     * 
     * @param feature Feature.
     * 
     * @return True if data has feature and false otherwise.
     */
    default boolean has(Feature feature) {
        return feature.belongsTo(this);
    }
    
}
