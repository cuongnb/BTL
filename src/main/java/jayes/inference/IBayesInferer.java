/**
 * Copyright (c) 2011 Michael Kutschke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Contributors:
 * Michael Kutschke - initial API and implementation.
 */
package jayes.inference;

import jayes.BayesNet;
import jayes.BayesNode;

import java.util.Map;

public interface IBayesInferer {

    void setNetwork(BayesNet bayesNet);

    void setEvidence(Map<BayesNode, String/*outcome*/> evidence);

    void addEvidence(BayesNode node, String outcome);

    double[] getBeliefs(BayesNode node);

    Map<BayesNode, String> getEvidence();
}
