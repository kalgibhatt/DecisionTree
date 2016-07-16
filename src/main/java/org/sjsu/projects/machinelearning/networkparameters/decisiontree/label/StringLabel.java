package org.sjsu.projects.machinelearning.networkparameters.decisiontree.label;

/**
 * Simplest possible label. Simply labels data as true or false.
 * 
 * @author Ignas
 *
 */
public class StringLabel extends Label {
    
    /** Label. */
    private String label;
    
    /**
     * Constructor.
     */
    private StringLabel(String label) {
        super();
        this.label = label;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrintValue() {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return String.valueOf(label);
    }
    
    /**
     * Static factory method.
     */
    public static Label newLabel(String label) {
        return new StringLabel(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return label.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringLabel other = (StringLabel) obj;
        if (label.equals(other.label))
            return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StringLabel [label=" + label + "]";
    }

}
