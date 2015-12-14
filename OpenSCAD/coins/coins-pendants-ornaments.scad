//
// This OpenScad script generates coins, pendants, and ornaments.
// 
// Author: Roberto Marquez - http://electronics.onebeartoe.org/
//
// This OpenSCAD script is a derivative of:
//
// Customizable Gimbal v1.0
// by TheNewHobbyist 2014 (http://thenewhobbyist.com)
// http://www.thingiverse.com/thing:340948
// https://github.com/thenewhobbyist/OpenSCAD/tree/master/Customizable%20Gimble
//
// "Customizable Gimbal" as well as this script are licensed under a 
// Creative Commons Attribution-NonCommercial 3.0 Unported License.
//
// TheNewHobbyist's Inspiration and attribution:
//
// Printrbot Gimbal by DesignMakeTeach 
// http://www.thingiverse.com/thing:317778
// Licensed under the Creative Commons - Attribution - Share Alike license.
//
// Stencil-o-Matic by Benjamin
// http://www.thingiverse.com/thing:55821
// Licensed under the Creative Commons - Attribution - Non-Commercial license.

use <spur/spurs-rotated/spurs-rotated.scad>;

/* [Dimensions] */

// Move image left/right
Move_X = 0; // [-50:50]

// Move image up/down
Move_Y = 0; // [-50:50]

// Resize your image
image_size = 26.5; // [1:50]

includeGimbal = "yes"; // [yes, no]

/* [Hidden] */

stencil_thickness = 5;
offX = 0;
offY = 0;
margin = 0;

input_width = 133;//points_array[0][0];

coinScale =  0.2;//0.199248;//image_size/input_width;

$fn=100;

ornament();

module ornament(height = 3)
{
    {
        union()
        {
            if(includeGimbal == "yes")
            {
                ring1(height);
                ring2(height);
                ring3(height);
            }

            difference()
            {
                color("blue") 
                pattern(height);
                
                clipOversizedImages(center=true);
            }
        }
    }    
}

module pattern(height)
{
    union()
    {
        echo(coinScale);
    	scale([coinScale, -coinScale, 1]) 
    	{
    		union() 
    		{
    		    coin(height=height);
			}
		}
	}
}

module clipOversizedImages()
{ 
	difference()
	{
		cylinder(r=200, h=10, center=true);
		cylinder(r=11, h=11, center=true);
	}
}

module ring1(height)
{
	difference()
	{
    	cylinder(r=19.35, h=height, center=true);
    	
    	cylinder(r=17.35, h=56, center=true);
    	translate([17.45,0,0]) rotate([0,90,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
    	translate([-17.45,0,0]) rotate([0,270,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	}
}

module ring2(height)
{
	translate([16.95,0,0]) rotate([0,90,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	translate([-16.95,0,0]) rotate([0,270,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	difference()
	{
		cylinder(r=15.95, h=height, center=true);
		cylinder(r=13.95, h=56, center=true);
		translate([0,-14.2,0]) rotate([90,0,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
		translate([0,14.2,0]) rotate([270,0,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	}
}

module ring3(height)
{
	translate([0,13.75,0]) rotate([270,0,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	translate([0,-13.75,0]) rotate([90,0,0]) cylinder(r1=1.7, r2=0, h=2.1, center=true);
	difference()
	{
		cylinder(r=12.75, h=height, center=true);
		cylinder(r=10.75, h=56, center=true);
	}
}