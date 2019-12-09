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

package org.apache.ignite.internal.processors.query.calcite.rule;

import java.util.List;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.ignite.internal.util.typedef.F;

/**
 *
 */
public abstract class AbstractVariableConverter extends ConverterRule {
    protected AbstractVariableConverter(Class<? extends RelNode> clazz, RelTrait in, RelTrait out, String descriptionPrefix) {
        super(clazz, in, out, descriptionPrefix);
    }

    @Override public void onMatch(RelOptRuleCall call) {
        RelNode rel = call.rel(0);
        if (rel.getTraitSet().contains(getInTrait())) {
            for (RelNode newRel : convert(rel, false))
                call.transformTo(newRel);
        }
    }

    @Override public RelNode convert(RelNode rel) {
        return F.first(convert(rel, true));
    }

    public abstract List<RelNode> convert(RelNode rel, boolean firstOnly);
}
