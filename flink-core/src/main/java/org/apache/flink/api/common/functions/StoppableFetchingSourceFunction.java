/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.flink.api.common.functions;

import org.apache.flink.annotation.PublicEvolving;

/**
 * Must be implemented by functions wich can stop fetching source, eg, source functions of streaming jobs.
 * The method {@link #stopFetching()} will be called when the job received the CANCEL with savepoint signal.
 * On this signal, the source function must stop emitting new data without terminating
 */
@PublicEvolving
public interface StoppableFetchingSourceFunction {

	/**
	 * Stops fetching the source.
	 * <p>
	 * Most streaming sources will have a while loop inside the {@code run()} method. You need to ensure that the source
	 * will break out of this loop without termintating the function (should be done by the {@code cancel()} method)
	 * No termination allow source function to continue to trigger checkpoints (must be a last savepoint)
	 * <p>
	 * <strong>The call to {@code stopFetching()} can block and can throw exception.</strong>
	 */
	void stopFetching();
}
