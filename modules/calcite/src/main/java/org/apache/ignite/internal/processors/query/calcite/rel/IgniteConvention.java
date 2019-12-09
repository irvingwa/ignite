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

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.plan.volcano.AbstractConverter;

/**
 *
 */
public class IgniteConvention extends Convention.Impl {
    public static final Convention INSTANCE = new IgniteConvention();

    private IgniteConvention() {
        super("IGNITE", IgniteRel.class);
    }

    @Override public void register(RelOptPlanner planner) {
        planner.addRule(AbstractConverter.ExpandConversionRule.INSTANCE);
    }

    @Override public boolean useAbstractConvertersForConversion(RelTraitSet fromTraits, RelTraitSet toTraits) {
        return fromTraits.contains(INSTANCE) && toTraits.contains(INSTANCE);
    }
}
