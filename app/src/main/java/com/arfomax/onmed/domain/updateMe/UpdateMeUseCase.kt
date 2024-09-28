package com.arfomax.onmed.domain.updateMe

import com.arfomax.onmed.data.network.updateMe.UpdateMeModel
import javax.inject.Inject

class UpdateMeUseCase @Inject constructor(private val updateMeRepository: UpdateMeRepository) {

    suspend operator fun invoke(updateMeModel: UpdateMeModel) = updateMeRepository.updateMe(updateMeModel)
}