package io.github.hectorvent.floci.services.appsync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiAssociation {
    private String associationId;
    private String apiId;
    private String sourceApiId;
    private String sourceApiArn;
    private String description;
    private String status = "MERGED";

    public String getAssociationId() { return associationId; }
    public void setAssociationId(String associationId) { this.associationId = associationId; }

    public String getApiId() { return apiId; }
    public void setApiId(String apiId) { this.apiId = apiId; }

    public String getSourceApiId() { return sourceApiId; }
    public void setSourceApiId(String sourceApiId) { this.sourceApiId = sourceApiId; }

    public String getSourceApiArn() { return sourceApiArn; }
    public void setSourceApiArn(String sourceApiArn) { this.sourceApiArn = sourceApiArn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
