# Types

All types are represented using the trait TypeDeclaration. There are many forms of type declaration but to simplify we can group them as either Apex related or other metadata related. The classes BasicTypeDeclaration and InnerTypeDeclaration are used to provide default implementations of TypeDeclarations features but have no structural significance. 

# Metadata Types

The diagram below shows the main TypeDeclarations used. The purpose of most of these should be fairly obvious from the naming. They are created in response to certain types of metadata being loaded and are visible because they can be referenced from Apex code. For example, LabelDeclaration provides an implementation of System.Label in Apex and fields on this TypeDeclaration provide access to individual fields.

The DependentType trait provides additional means for handling 'type level' dependencies, there is a separate section on dependency handling later in this note.

```
TypeDeclaration (T)
└───BasicTypeDeclaration
    └───InnerBasicTypeDeclaration
        └───Component
        └───GhostedComponents
        └───GhostedInterviews
        └───GhostedLabels
        └───Interview
        └───PackageComponents
        └───PackageInterviews
        └───PackageLabels
    └───AnyDeclaration
    └───SObjectFieldRowClause
    └───SObjectFields
    └───SObjectTypeFieldSets
    └───SObjectTypeFields
    └───SObjectTypeImpl
    └───SchemaSObjectType
    └───TriggerContext
    └───PlatformTypeDeclaration
        └───GenericPlatformTypeDeclaration
    └───SObjectFieldRowClause
    └───SObjectFields
    └───SchemaSObjectType
    └───DependentType (T)
        └───InterviewDeclaration
        └───LabelDeclaration
        └───PageDeclaration
        └───ComponentDeclaration        
        └───SObjectLikeDeclaration
            └───SObjectDeclaration
            └───GhostSObjectDeclaration                    
```   

# Apex Types

As you may expect, the TypeDeclarations supporting Apex are a little richer than for the general metadata types. For classes, interface & enums there are 'Full' models for when they are loaded via parsing and an all-in-one style 'Summary' model for when the TypeDeclarations are loaded from the cache. For triggers there is only the 'Full' model as currently they are not cached. Note that both Full & Summary style TypeDeclarations also use DependentType.

Within the library we have the ability to convert between Full & Summary types either by creating a Summary from a Full type or by re-parsing so that we can discard a Summary in favour of Full type declaration.

```
TypeDeclaration (T)
└───DependentType (T)
    └───ApexDeclaration extends      
        └───ApexClassDeclaration (T)
            └───FullDeclaration extends ApexFullDeclaration (T)       
                └───ClassDeclaration
                └───InterfaceDeclaration
                └───EnumDeclaration
        └───ApexTriggerDeclaration (T)
            └───TriggerDeclaration extends ApexFullDeclaration (T)
        └───SummaryDeclaration
```

# Dependency Management

Dependencies are recordable between elements with the 'Dependent' and 'DependencyHolder' traits. The relationship is strong from Dependent->DependencyHolder and weak for DependencyHolder->Dependent to allow for garbage collection when dependents are replaced. TypeDeclaration is both a DependencyHolder and a Dependent but this over generalises the actual use of the model.

In practice each class body declaration in Apex code acts as a DependencyHolder where the Dependents are found when that class body declaration is validated. A Dependent in this context may be a TypeDeclaration but it could also be a more fine grained element such as a method.

For external & internal use, the DependentHolder->Dependent relationships are rolled up and stored on TypeDeclarations that implement DependentType. This model is uni-directional in that it only records holder relationships and it records them using TypeId references rather than object<->object lookups. 

For example, if Apex code references a Label there will be Dependent<->DependencyHolder bi-directional relationship between that Apex code block and the specific Label and a type level relationship from Labels back to the Apex class via it's TypeId. The detailed relationship can be be used for forward/reverse reporting, whilst the type level relationship only supports reverse reporting and importantly support invalidation handling.

## Invalidation Handling
The dependency relationship enable a form of invalidation handling to ensure that we are always reporting the correct diagnostics during IDE editing. Briefly, when metadata is changed (including Apex code) we automatically re-validate any metadata that could be impacted by following the type level dependency holder relationships.

For example, lets say the Labels available were changed, to re-validate our Apex code we find all classes that may be impacted via the type level dependency holder relationships on DependentType. The re-validation of these Apex classes will cause them to report diagnostics for any Labels they depend on that are no longer available.



