package org.tensorflow.demo.dagger.modules

import android.content.Context

import org.tensorflow.demo.utils.PreferencesHelper
import org.tensorflow.demo.utils.RatingHelper

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by bridou_n on 03/04/2017.
 */

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferencesHelper(ctx: Context): PreferencesHelper {
        return PreferencesHelper(ctx)
    }

    @Provides
    @Singleton
    fun providesRatingHelper(ctx: Context): RatingHelper {
        return RatingHelper(ctx)
    }
}
