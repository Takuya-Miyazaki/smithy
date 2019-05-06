/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.model.validation.validators;

import java.util.List;
import java.util.stream.Collectors;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidationEvent;

/**
 * Ensures that trait definitions are not both {@code inherited} and
 * {@code reified}.
 */
public final class TraitDefinitionValidator extends AbstractValidator {
    @Override
    public List<ValidationEvent> validate(Model model) {
        return model.getTraitDefinitions().stream()
                .filter(def -> def.isInherited() && def.isReified())
                .map(def -> ValidationEvent.builder()
                        .eventId(getName())
                        .sourceLocation(def)
                        .severity(Severity.ERROR)
                        .message(String.format("Trait definition `%s` is marked as both inherited and reified",
                                               def.getFullyQualifiedName()))
                        .build())
                .collect(Collectors.toList());
    }
}
