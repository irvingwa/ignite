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

import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelShuttleImpl;

/**
 *
 */
public class IgniteRelShuttle extends RelShuttleImpl {
    public RelNode visit(IgniteExchange rel) {
        return visitChild(rel, 0, rel.getInput());
    }

    public RelNode visit(IgniteFilter rel) {
        return visitChild(rel, 0, rel.getInput());
    }

    public RelNode visit(IgniteProject rel) {
        return visitChild(rel, 0, rel.getInput());
    }

    public RelNode visit(IgniteReceiver rel) {
        return rel;
    }

    public RelNode visit(IgniteSender rel) {
        return visitChild(rel, 0, rel.getInput());
    }

    public RelNode visit(IgniteTableScan rel) {
        return rel;
    }

    public RelNode visit(IgniteJoin rel) {
        return visitChildren(rel);
    }

    @Override public RelNode visit(RelNode rel) {
        if (rel instanceof IgniteExchange)
            return visit((IgniteExchange)rel);
        if (rel instanceof IgniteFilter)
            return visit((IgniteFilter)rel);
        if (rel instanceof IgniteProject)
            return visit((IgniteProject)rel);
        if (rel instanceof IgniteReceiver)
            return visit((IgniteReceiver)rel);
        if (rel instanceof IgniteSender)
            return visit((IgniteSender)rel);
        if (rel instanceof IgniteTableScan)
            return visit((IgniteTableScan)rel);
        if (rel instanceof IgniteJoin)
            return visit((IgniteJoin)rel);

        return visitOther(rel);
    }

    protected RelNode visitOther(RelNode rel) {
        return super.visit(rel);
    }
}
