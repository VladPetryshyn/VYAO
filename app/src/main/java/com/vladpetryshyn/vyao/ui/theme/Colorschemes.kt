package com.vladpetryshyn.vyao.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class RedColorScheme() {
    companion object {
        val md_theme_light_primary = Color(0xFFC00100)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFFFFDAD4)
        val md_theme_light_onPrimaryContainer = Color(0xFF410000)
        val md_theme_light_secondary = Color(0xFF775651)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFFFDAD4)
        val md_theme_light_onSecondaryContainer = Color(0xFF2C1512)
        val md_theme_light_tertiary = Color(0xFF705C2E)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFFBDFA6)
        val md_theme_light_onTertiaryContainer = Color(0xFF251A00)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFFFBFF)
        val md_theme_light_onBackground = Color(0xFF201A19)
        val md_theme_light_surface = Color(0xFFFFFBFF)
        val md_theme_light_onSurface = Color(0xFF201A19)
        val md_theme_light_surfaceVariant = Color(0xFFF5DDDA)
        val md_theme_light_onSurfaceVariant = Color(0xFF534341)
        val md_theme_light_outline = Color(0xFF857370)
        val md_theme_light_inverseOnSurface = Color(0xFFFBEEEC)
        val md_theme_light_inverseSurface = Color(0xFF362F2E)
        val md_theme_light_inversePrimary = Color(0xFFFFB4A8)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFFC00100)
        val md_theme_light_outlineVariant = Color(0xFFD8C2BE)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFFFFB4A8)
        val md_theme_dark_onPrimary = Color(0xFF690100)
        val md_theme_dark_primaryContainer = Color(0xFF930100)
        val md_theme_dark_onPrimaryContainer = Color(0xFFFFDAD4)
        val md_theme_dark_secondary = Color(0xFFE7BDB6)
        val md_theme_dark_onSecondary = Color(0xFF442925)
        val md_theme_dark_secondaryContainer = Color(0xFF5D3F3B)
        val md_theme_dark_onSecondaryContainer = Color(0xFFFFDAD4)
        val md_theme_dark_tertiary = Color(0xFFDEC48C)
        val md_theme_dark_onTertiary = Color(0xFF3E2E04)
        val md_theme_dark_tertiaryContainer = Color(0xFF564419)
        val md_theme_dark_onTertiaryContainer = Color(0xFFFBDFA6)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF201A19)
        val md_theme_dark_onBackground = Color(0xFFEDE0DD)
        val md_theme_dark_surface = Color(0xFF201A19)
        val md_theme_dark_onSurface = Color(0xFFEDE0DD)
        val md_theme_dark_surfaceVariant = Color(0xFF534341)
        val md_theme_dark_onSurfaceVariant = Color(0xFFD8C2BE)
        val md_theme_dark_outline = Color(0xFFA08C89)
        val md_theme_dark_inverseOnSurface = Color(0xFF201A19)
        val md_theme_dark_inverseSurface = Color(0xFFEDE0DD)
        val md_theme_dark_inversePrimary = Color(0xFFC00100)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFFFFB4A8)
        val md_theme_dark_outlineVariant = Color(0xFF534341)
        val md_theme_dark_scrim = Color(0xFF000000)

        val seed = Color(0xFFFF0000)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


        val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

class OrangeColorScheme() {
    companion object {
        val md_theme_light_primary = Color(0xFF855400)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFFFFDDB7)
        val md_theme_light_onPrimaryContainer = Color(0xFF2A1700)
        val md_theme_light_secondary = Color(0xFF705B41)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFFCDEBC)
        val md_theme_light_onSecondaryContainer = Color(0xFF281805)
        val md_theme_light_tertiary = Color(0xFF53643E)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFD6E9B9)
        val md_theme_light_onTertiaryContainer = Color(0xFF121F03)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFFFBFF)
        val md_theme_light_onBackground = Color(0xFF1F1B16)
        val md_theme_light_surface = Color(0xFFFFFBFF)
        val md_theme_light_onSurface = Color(0xFF1F1B16)
        val md_theme_light_surfaceVariant = Color(0xFFF0E0D0)
        val md_theme_light_onSurfaceVariant = Color(0xFF504539)
        val md_theme_light_outline = Color(0xFF827568)
        val md_theme_light_inverseOnSurface = Color(0xFFF9EFE7)
        val md_theme_light_inverseSurface = Color(0xFF352F2A)
        val md_theme_light_inversePrimary = Color(0xFFFFB95C)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFF855400)
        val md_theme_light_outlineVariant = Color(0xFFD4C4B5)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFFFFB95C)
        val md_theme_dark_onPrimary = Color(0xFF462A00)
        val md_theme_dark_primaryContainer = Color(0xFF653E00)
        val md_theme_dark_onPrimaryContainer = Color(0xFFFFDDB7)
        val md_theme_dark_secondary = Color(0xFFDFC2A2)
        val md_theme_dark_onSecondary = Color(0xFF3F2D17)
        val md_theme_dark_secondaryContainer = Color(0xFF57432B)
        val md_theme_dark_onSecondaryContainer = Color(0xFFFCDEBC)
        val md_theme_dark_tertiary = Color(0xFFBACD9F)
        val md_theme_dark_onTertiary = Color(0xFF263514)
        val md_theme_dark_tertiaryContainer = Color(0xFF3C4C28)
        val md_theme_dark_onTertiaryContainer = Color(0xFFD6E9B9)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF1F1B16)
        val md_theme_dark_onBackground = Color(0xFFEBE1D9)
        val md_theme_dark_surface = Color(0xFF1F1B16)
        val md_theme_dark_onSurface = Color(0xFFEBE1D9)
        val md_theme_dark_surfaceVariant = Color(0xFF504539)
        val md_theme_dark_onSurfaceVariant = Color(0xFFD4C4B5)
        val md_theme_dark_outline = Color(0xFF9C8E80)
        val md_theme_dark_inverseOnSurface = Color(0xFF1F1B16)
        val md_theme_dark_inverseSurface = Color(0xFFEBE1D9)
        val md_theme_dark_inversePrimary = Color(0xFF855400)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFFFFB95C)
        val md_theme_dark_outlineVariant = Color(0xFF504539)
        val md_theme_dark_scrim = Color(0xFF000000)


        val seed = Color(0xFFFFA500)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


        val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

class YellowColorScheme() {
    companion object {
        val md_theme_light_primary = Color(0xFF626200)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFFEAEA00)
        val md_theme_light_onPrimaryContainer = Color(0xFF1D1D00)
        val md_theme_light_secondary = Color(0xFF606043)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFE7E4BF)
        val md_theme_light_onSecondaryContainer = Color(0xFF1D1D06)
        val md_theme_light_tertiary = Color(0xFF3D6657)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFBFECD8)
        val md_theme_light_onTertiaryContainer = Color(0xFF002117)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFFFBFF)
        val md_theme_light_onBackground = Color(0xFF1C1C17)
        val md_theme_light_surface = Color(0xFFFFFBFF)
        val md_theme_light_onSurface = Color(0xFF1C1C17)
        val md_theme_light_surfaceVariant = Color(0xFFE6E3D1)
        val md_theme_light_onSurfaceVariant = Color(0xFF48473A)
        val md_theme_light_outline = Color(0xFF797869)
        val md_theme_light_inverseOnSurface = Color(0xFFF4F0E8)
        val md_theme_light_inverseSurface = Color(0xFF31312B)
        val md_theme_light_inversePrimary = Color(0xFFCDCD00)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFF626200)
        val md_theme_light_outlineVariant = Color(0xFFCAC7B6)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFFCDCD00)
        val md_theme_dark_onPrimary = Color(0xFF323200)
        val md_theme_dark_primaryContainer = Color(0xFF494900)
        val md_theme_dark_onPrimaryContainer = Color(0xFFEAEA00)
        val md_theme_dark_secondary = Color(0xFFCAC8A5)
        val md_theme_dark_onSecondary = Color(0xFF323218)
        val md_theme_dark_secondaryContainer = Color(0xFF49482D)
        val md_theme_dark_onSecondaryContainer = Color(0xFFE7E4BF)
        val md_theme_dark_tertiary = Color(0xFFA4D0BD)
        val md_theme_dark_onTertiary = Color(0xFF0B372A)
        val md_theme_dark_tertiaryContainer = Color(0xFF254E40)
        val md_theme_dark_onTertiaryContainer = Color(0xFFBFECD8)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF1C1C17)
        val md_theme_dark_onBackground = Color(0xFFE6E2D9)
        val md_theme_dark_surface = Color(0xFF1C1C17)
        val md_theme_dark_onSurface = Color(0xFFE6E2D9)
        val md_theme_dark_surfaceVariant = Color(0xFF48473A)
        val md_theme_dark_onSurfaceVariant = Color(0xFFCAC7B6)
        val md_theme_dark_outline = Color(0xFF939182)
        val md_theme_dark_inverseOnSurface = Color(0xFF1C1C17)
        val md_theme_dark_inverseSurface = Color(0xFFE6E2D9)
        val md_theme_dark_inversePrimary = Color(0xFF626200)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFFCDCD00)
        val md_theme_dark_outlineVariant = Color(0xFF48473A)
        val md_theme_dark_scrim = Color(0xFF000000)


        val seed = Color(0xFFFFFF00)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


        val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

class BlueColorScheme {
    companion object {
        val md_theme_light_primary = Color(0xFF343DFF)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFFE0E0FF)
        val md_theme_light_onPrimaryContainer = Color(0xFF00006E)
        val md_theme_light_secondary = Color(0xFF5C5D72)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFE1E0F9)
        val md_theme_light_onSecondaryContainer = Color(0xFF191A2C)
        val md_theme_light_tertiary = Color(0xFF78536B)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFFFD8EE)
        val md_theme_light_onTertiaryContainer = Color(0xFF2E1126)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFFFBFF)
        val md_theme_light_onBackground = Color(0xFF1B1B1F)
        val md_theme_light_surface = Color(0xFFFFFBFF)
        val md_theme_light_onSurface = Color(0xFF1B1B1F)
        val md_theme_light_surfaceVariant = Color(0xFFE4E1EC)
        val md_theme_light_onSurfaceVariant = Color(0xFF46464F)
        val md_theme_light_outline = Color(0xFF777680)
        val md_theme_light_inverseOnSurface = Color(0xFFF3EFF4)
        val md_theme_light_inverseSurface = Color(0xFF303034)
        val md_theme_light_inversePrimary = Color(0xFFBEC2FF)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFF343DFF)
        val md_theme_light_outlineVariant = Color(0xFFC7C5D0)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFFBEC2FF)
        val md_theme_dark_onPrimary = Color(0xFF0001AC)
        val md_theme_dark_primaryContainer = Color(0xFF0000EF)
        val md_theme_dark_onPrimaryContainer = Color(0xFFE0E0FF)
        val md_theme_dark_secondary = Color(0xFFC5C4DD)
        val md_theme_dark_onSecondary = Color(0xFF2E2F42)
        val md_theme_dark_secondaryContainer = Color(0xFF444559)
        val md_theme_dark_onSecondaryContainer = Color(0xFFE1E0F9)
        val md_theme_dark_tertiary = Color(0xFFE8B9D5)
        val md_theme_dark_onTertiary = Color(0xFF46263B)
        val md_theme_dark_tertiaryContainer = Color(0xFF5E3C52)
        val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8EE)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF1B1B1F)
        val md_theme_dark_onBackground = Color(0xFFE5E1E6)
        val md_theme_dark_surface = Color(0xFF1B1B1F)
        val md_theme_dark_onSurface = Color(0xFFE5E1E6)
        val md_theme_dark_surfaceVariant = Color(0xFF46464F)
        val md_theme_dark_onSurfaceVariant = Color(0xFFC7C5D0)
        val md_theme_dark_outline = Color(0xFF91909A)
        val md_theme_dark_inverseOnSurface = Color(0xFF1B1B1F)
        val md_theme_dark_inverseSurface = Color(0xFFE5E1E6)
        val md_theme_dark_inversePrimary = Color(0xFF343DFF)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFFBEC2FF)
        val md_theme_dark_outlineVariant = Color(0xFF46464F)
        val md_theme_dark_scrim = Color(0xFF000000)


        val seed = Color(0xFF0000FF)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


         val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

class PurpleColorScheme {
    companion object {
        val md_theme_light_primary = Color(0xFF9E2A9B)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFFFFD7F5)
        val md_theme_light_onPrimaryContainer = Color(0xFF380038)
        val md_theme_light_secondary = Color(0xFF6D5869)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFF7DAEF)
        val md_theme_light_onSecondaryContainer = Color(0xFF271624)
        val md_theme_light_tertiary = Color(0xFF825345)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFFFDBD1)
        val md_theme_light_onTertiaryContainer = Color(0xFF321208)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFFFBFF)
        val md_theme_light_onBackground = Color(0xFF1E1A1D)
        val md_theme_light_surface = Color(0xFFFFFBFF)
        val md_theme_light_onSurface = Color(0xFF1E1A1D)
        val md_theme_light_surfaceVariant = Color(0xFFEEDEE7)
        val md_theme_light_onSurfaceVariant = Color(0xFF4E444B)
        val md_theme_light_outline = Color(0xFF80747C)
        val md_theme_light_inverseOnSurface = Color(0xFFF8EEF2)
        val md_theme_light_inverseSurface = Color(0xFF342F32)
        val md_theme_light_inversePrimary = Color(0xFFFFAAF3)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFF9E2A9B)
        val md_theme_light_outlineVariant = Color(0xFFD1C2CB)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFFFFAAF3)
        val md_theme_dark_onPrimary = Color(0xFF5B005B)
        val md_theme_dark_primaryContainer = Color(0xFF810181)
        val md_theme_dark_onPrimaryContainer = Color(0xFFFFD7F5)
        val md_theme_dark_secondary = Color(0xFFDABFD2)
        val md_theme_dark_onSecondary = Color(0xFF3D2B3A)
        val md_theme_dark_secondaryContainer = Color(0xFF554151)
        val md_theme_dark_onSecondaryContainer = Color(0xFFF7DAEF)
        val md_theme_dark_tertiary = Color(0xFFF5B8A7)
        val md_theme_dark_onTertiary = Color(0xFF4C261B)
        val md_theme_dark_tertiaryContainer = Color(0xFF663C2F)
        val md_theme_dark_onTertiaryContainer = Color(0xFFFFDBD1)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF1E1A1D)
        val md_theme_dark_onBackground = Color(0xFFE9E0E4)
        val md_theme_dark_surface = Color(0xFF1E1A1D)
        val md_theme_dark_onSurface = Color(0xFFE9E0E4)
        val md_theme_dark_surfaceVariant = Color(0xFF4E444B)
        val md_theme_dark_onSurfaceVariant = Color(0xFFD1C2CB)
        val md_theme_dark_outline = Color(0xFF9A8D95)
        val md_theme_dark_inverseOnSurface = Color(0xFF1E1A1D)
        val md_theme_dark_inverseSurface = Color(0xFFE9E0E4)
        val md_theme_dark_inversePrimary = Color(0xFF9E2A9B)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFFFFAAF3)
        val md_theme_dark_outlineVariant = Color(0xFF4E444B)
        val md_theme_dark_scrim = Color(0xFF000000)


        val seed = Color(0xFF800080)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


        val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

class GreenColorScheme {
    companion object {
        val md_theme_light_primary = Color(0xFF026E00)
        val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        val md_theme_light_primaryContainer = Color(0xFF77FF61)
        val md_theme_light_onPrimaryContainer = Color(0xFF002200)
        val md_theme_light_secondary = Color(0xFF54634D)
        val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        val md_theme_light_secondaryContainer = Color(0xFFD7E8CD)
        val md_theme_light_onSecondaryContainer = Color(0xFF121F0E)
        val md_theme_light_tertiary = Color(0xFF386568)
        val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        val md_theme_light_tertiaryContainer = Color(0xFFBCEBEE)
        val md_theme_light_onTertiaryContainer = Color(0xFF002022)
        val md_theme_light_error = Color(0xFFBA1A1A)
        val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        val md_theme_light_onError = Color(0xFFFFFFFF)
        val md_theme_light_onErrorContainer = Color(0xFF410002)
        val md_theme_light_background = Color(0xFFFCFDF6)
        val md_theme_light_onBackground = Color(0xFF1A1C18)
        val md_theme_light_surface = Color(0xFFFCFDF6)
        val md_theme_light_onSurface = Color(0xFF1A1C18)
        val md_theme_light_surfaceVariant = Color(0xFFDFE4D7)
        val md_theme_light_onSurfaceVariant = Color(0xFF43483F)
        val md_theme_light_outline = Color(0xFF73796E)
        val md_theme_light_inverseOnSurface = Color(0xFFF1F1EB)
        val md_theme_light_inverseSurface = Color(0xFF2F312D)
        val md_theme_light_inversePrimary = Color(0xFF02E600)
        val md_theme_light_shadow = Color(0xFF000000)
        val md_theme_light_surfaceTint = Color(0xFF026E00)
        val md_theme_light_outlineVariant = Color(0xFFC3C8BC)
        val md_theme_light_scrim = Color(0xFF000000)

        val md_theme_dark_primary = Color(0xFF02E600)
        val md_theme_dark_onPrimary = Color(0xFF013A00)
        val md_theme_dark_primaryContainer = Color(0xFF015300)
        val md_theme_dark_onPrimaryContainer = Color(0xFF77FF61)
        val md_theme_dark_secondary = Color(0xFFBBCBB2)
        val md_theme_dark_onSecondary = Color(0xFF263422)
        val md_theme_dark_secondaryContainer = Color(0xFF3C4B37)
        val md_theme_dark_onSecondaryContainer = Color(0xFFD7E8CD)
        val md_theme_dark_tertiary = Color(0xFFA0CFD2)
        val md_theme_dark_onTertiary = Color(0xFF003739)
        val md_theme_dark_tertiaryContainer = Color(0xFF1E4D50)
        val md_theme_dark_onTertiaryContainer = Color(0xFFBCEBEE)
        val md_theme_dark_error = Color(0xFFFFB4AB)
        val md_theme_dark_errorContainer = Color(0xFF93000A)
        val md_theme_dark_onError = Color(0xFF690005)
        val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        val md_theme_dark_background = Color(0xFF1A1C18)
        val md_theme_dark_onBackground = Color(0xFFE2E3DC)
        val md_theme_dark_surface = Color(0xFF1A1C18)
        val md_theme_dark_onSurface = Color(0xFFE2E3DC)
        val md_theme_dark_surfaceVariant = Color(0xFF43483F)
        val md_theme_dark_onSurfaceVariant = Color(0xFFC3C8BC)
        val md_theme_dark_outline = Color(0xFF8D9387)
        val md_theme_dark_inverseOnSurface = Color(0xFF1A1C18)
        val md_theme_dark_inverseSurface = Color(0xFFE2E3DC)
        val md_theme_dark_inversePrimary = Color(0xFF026E00)
        val md_theme_dark_shadow = Color(0xFF000000)
        val md_theme_dark_surfaceTint = Color(0xFF02E600)
        val md_theme_dark_outlineVariant = Color(0xFF43483F)
        val md_theme_dark_scrim = Color(0xFF000000)

        val LightColors = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )


        val DarkColors = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    }
}

fun createJustBlackColorScheme(
    primary: String
): ColorScheme {
    val primaryColor = Color(android.graphics.Color.parseColor(primary))
    return darkColorScheme(
        primary = primaryColor,
        surface = Color.Black,
        background = Color.Black,
        surfaceVariant = Color.Black,
        primaryContainer = Color.Black,
        onSecondary = Color.Black,
        onSurface = Color.White,
        onBackground = Color.White,
        onPrimary = Color.White,
        secondaryContainer = Color.Black
    )
}