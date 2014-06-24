package crawlers.rest.fb;

import java.util.HashMap;
import java.util.Map;

class FBQuery {

	private Map<String,String> parameters;
	private String query;
	
	/**
	 * Initializes a query object
	 * @param node - the node which can be of the form {object-id} only or
	 * 				 {object-id}/subnode
	 */
	public FBQuery(String node) {
		this.query = node;
		this.parameters = new HashMap<String,String>();
	}
	

	/**
	 * Builds a query with the parameters introduced
	 */
	public void build() {
		if(this.hasParameters()) {
			query += "?";
			for(String field: parameters.keySet()) {
				query+=field+"="+parameters.get(field)+"&";
			}
			//just taking that extra '&'
			query = query.substring(0, query.length()-1);
		}
	}
	
	/**
	 * Adds a parameter
	 */
	public void addParameter(String field, String value) {
		parameters.put(field,value);
	}
	
	/**
	 * Checks if the query has any parameters
	 */
	public boolean hasParameters() {
		return !parameters.isEmpty();
	}
	
	/**
	 * Returns the query as a string
	 */
	public String toString() {
		return query;
	}
}
