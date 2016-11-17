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
package jayes.sampling;


import jayes.BayesNet;
import jayes.BayesNode;

import java.util.Map;

public interface ISampler {

    /**
     * @deprecated still here for compatibility reasons, but for the sake of uniformity, setNetwork should be used
     */
    @Deprecated
    void setBN(BayesNet net);

    void setNetwork(BayesNet net);

    void setEvidence(Map<BayesNode, String> evidence);

    Map<BayesNode, String> sample();

    void seed(long seed);

}
