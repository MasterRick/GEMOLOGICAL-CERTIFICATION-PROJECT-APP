package com.certgem.data.model
import java.time.LocalDate

data class GemSpecifications(
    val gemName: String, val weight: String, val measurements: String, val shape: String,
    val color: String, val transparency: String, val clarity: String
)
data class EvaluationResult(
    val species: String, val variety: String, val treatment: String, val origin: String
)
data class AdditionalInfo(
    val ownerName: String, val comments: String, val observations: String
)
data class Certificate(
    val id: String, val itemNumber: String, val issueDate: LocalDate,
    val specifications: GemSpecifications, val evaluation: EvaluationResult, val additionalInfo: AdditionalInfo
)