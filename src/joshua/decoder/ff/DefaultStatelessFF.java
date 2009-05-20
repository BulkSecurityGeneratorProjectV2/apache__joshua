/* This file is part of the Joshua Machine Translation System.
 * 
 * Joshua is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307 USA
 */

package joshua.decoder.ff;

import java.util.ArrayList;
import java.util.logging.Logger;

import joshua.decoder.ff.tm.Rule;
import joshua.decoder.hypergraph.HyperEdge;


/**
 * 
 * @author Zhifei Li, <zhifei.work@gmail.com>
 * @version $LastChangedDate$
 */

public abstract class DefaultStatelessFF implements FeatureFunction {
	
	private static final Logger logger = Logger.getLogger(DefaultStatelessFF.class.getName());
	
	private      double weight = 0.0;
	private         int featureID;
	protected final int owner;
	
	public DefaultStatelessFF(final double weight, final int owner, int id) {
		this.weight    = weight;
		this.owner     = owner;
		this.featureID = id;
	}
	
	public final boolean isStateful() {
		return false;
	}
	
	public final double getWeight() {
		return this.weight;
	}
	
	public final void setWeight(final double weight) {
		this.weight = weight;
	}
	
	
	public final int getFeatureID() {
		return this.featureID;
	}
	
	public final void setFeatureID(final int id) {
		this.featureID = id;
	}
	
	
	/** Default behavior: ignore "edge". */
	public FFTransitionResult transition(HyperEdge edge, Rule rule, ArrayList<FFDPState> previous_states, int span_start, int span_end) {
		return transition(rule, previous_states, span_start,span_end);
	}
	
	/** Default behavior: ignore "edge". */
	public double finalTransition(HyperEdge edge, FFDPState state) {
		return finalTransition(state);
	}
	
	/**
	 * Generic transition for FeatureFunctions which are Stateless
	 * (1) use estimate() to get transition cost
	 * (2) no future cost estimation
	 */
	public StatelessFFTransitionResult transition(Rule rule, ArrayList<FFDPState> previous_states, int span_start, int span_end) {
		if (null != previous_states) {
			throw new IllegalArgumentException("transition: previous states for a stateless feature is NOT null");
		}
		StatelessFFTransitionResult result = new StatelessFFTransitionResult();
		result.setTransitionCost(this.estimate(rule));
		return result;
	}
	
	
	/** Default implementation of finalTransition **/
	public double finalTransition(FFDPState state) {
		if (null != state) {
			throw new IllegalArgumentException("finalTransition: state for a stateless feature is NOT null");
		}
		return 0.0;
	}
}
