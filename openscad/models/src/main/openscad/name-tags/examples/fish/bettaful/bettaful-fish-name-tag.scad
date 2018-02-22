
use <../../../name-tag.scad>

icons_xOffset = 34;
icons_xyScale = 0.65;
icons_yOffset = -2.5;

nametag(baseColor = "Blue",
	    baseHeight = 12,
        baseThickness = 2,
        baseWidth = 76,
      	bottomText = "Diego's Fish",
        bottomTextColor = "white",
        bottomTextFont = "Bauhaus 93", // "Arial", "Wingdings",
        bottomTextOffsetX = 0,
        bottomTextOffsetY = -7,
      	bottomTextSize = 7,
        chainLoop = false,
		xIconOffset = icons_xOffset,
		yIconOffset = icons_yOffset,
		leftIconHeight = 3,
      	leftIconType = "Fish",
		leftIconXyScale = icons_xyScale,
        letterThickness = 3,
      	rightIconType = "Fish",
        rightIconHeight = 3,
		rightIconXyScale = icons_xyScale,
		rightIconOffsetY = icons_yOffset,
        roundedCorners = true,
        showBorder = "No",
        topText = "Bettaful",
      	topTextOffsetY = 4,
        topTextSize = 11,
        topTextColor = "white",
        topTextFont = "Bauhaus 93"); // "Bauhaus 93" "Helvetica");
