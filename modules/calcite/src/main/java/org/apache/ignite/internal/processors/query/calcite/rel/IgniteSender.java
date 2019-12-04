/*
 * Copyright 2019 GridGain Systems, Inc. and Contributors.
 *
 * Licensed under the GridGain Community Edition License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.query.calcite.rel;

import java.util.List;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.SingleRel;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.ignite.internal.processors.query.calcite.metadata.IgniteMdDistribution;
import org.apache.ignite.internal.processors.query.calcite.splitter.Target;
import org.apache.ignite.internal.processors.query.calcite.trait.DistributionTraitDef;
import org.apache.ignite.internal.processors.query.calcite.util.RelImplementor;

/**
 *
 */
public final class IgniteSender extends SingleRel implements IgniteRel {
    private Target target;

    /**
     * Creates a <code>SingleRel</code>.
     * @param cluster Cluster this relational expression belongs to
     * @param traits Trait set.
     * @param input Input relational expression
     */
    public IgniteSender(RelOptCluster cluster, RelTraitSet traits, RelNode input) {
        super(cluster, traits, input);
    }

    private IgniteSender(RelOptCluster cluster, RelTraitSet traits, RelNode input, Target target) {
        super(cluster, traits, input);

        this.target = target;
    }

    @Override public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
        return new IgniteSender(getCluster(), traitSet, sole(inputs), target);
    }

    /** {@inheritDoc} */
    @Override public <T> T implement(RelImplementor<T> implementor) {
        return implementor.implement(this);
    }

    public void init(Target target) {
        this.target = target;
    }

    public Target target() {
        return target;
    }

    public static IgniteSender create(RelNode input, Target target) {
        RelOptCluster cluster = input.getCluster();
        RelMetadataQuery mq = cluster.getMetadataQuery();

        RelTraitSet traits = cluster.traitSet()
            .replace(IgniteRel.IGNITE_CONVENTION)
            .replaceIf(DistributionTraitDef.INSTANCE, () -> IgniteMdDistribution.distribution(input, mq));

        return new IgniteSender(cluster, traits, input, target);
    }
}
