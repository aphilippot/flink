/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.checkpoint;

import static org.apache.flink.util.Preconditions.checkNotNull;

import java.io.Serializable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.flink.runtime.jobgraph.tasks.StatefulTask;

/**
 * Options for performing the checkpoint.
 *
 * <p>The {@link CheckpointProperties} are related and cover properties that
 * are only relevant at the {@link CheckpointCoordinator}. These options are
 * relevant at the {@link StatefulTask} instances running on task managers.
 */
public class CheckpointOptions implements Serializable {

	private static final long serialVersionUID = 5010126558083292915L;

	/** Type of the checkpoint. */
	@Nonnull
	private final CheckpointType checkpointType;

	/** Target location for the checkpoint. */
	@Nullable
	private final String targetLocation;

	/** Indicate if the source must be stop before triggering the checkpoint (must be a savepoint) **/
	private final boolean stopSourceBeforeSavepoint;

	private CheckpointOptions(
			@Nonnull CheckpointType checkpointType,
			@Nullable  String targetLocation,
			boolean stopSourceBeforeSavepoint) {
		this.checkpointType = checkNotNull(checkpointType);
		this.targetLocation = targetLocation;
		this.stopSourceBeforeSavepoint = stopSourceBeforeSavepoint;
	}

	/**
	 * Returns the type of checkpoint to perform.
	 *
	 * @return Type of checkpoint to perform.
	 */
	@Nonnull
	public CheckpointType getCheckpointType() {
		return checkpointType;
	}

	/**
	 * Returns a custom target location or <code>null</code> if none
	 * was specified.
	 *
	 * @return A custom target location or <code>null</code>.
	 */
	@Nullable
	public String getTargetLocation() {
		return targetLocation;
	}

	/**
	 * Returns whether the source must be stopped before starting a savepoint.
	 *
	 * @return <code>true</code> if the source must be stopped before a savepoint, <code>false</code> otherwise.
	 */
	public boolean isStopSourceBeforeSavepoint() {
		return stopSourceBeforeSavepoint;
	}

	@Override
	public String toString() {
		return "CheckpointOptions(" + checkpointType + ")";
	}

	// ------------------------------------------------------------------------

	private static final CheckpointOptions FULL_CHECKPOINT =
		new CheckpointOptions(CheckpointType.FULL_CHECKPOINT, null, false);

	public static CheckpointOptions forFullCheckpoint() {
		return FULL_CHECKPOINT;
	}

	public static CheckpointOptions forSavepoint(String targetDirectory) {
		checkNotNull(targetDirectory, "targetDirectory");
		return new CheckpointOptions(CheckpointType.SAVEPOINT, targetDirectory, false);
	}

	public static CheckpointOptions forStopSourceSavepoint(String targetDirectory) {
		checkNotNull(targetDirectory, "targetDirectory");
		return new CheckpointOptions(CheckpointType.SAVEPOINT, targetDirectory, true);
	}

	// ------------------------------------------------------------------------

	/**
	 *  The type of checkpoint to perform.
	 */
	public enum CheckpointType {

		/** A full checkpoint. */
		FULL_CHECKPOINT,

		/** A savepoint. */
		SAVEPOINT;

	}

}
