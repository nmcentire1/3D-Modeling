
module usb210_filter_attachementOutline(h = 1)
{
	rotate([0, 0, -3.5])
	usb210_filter_rawAttachementOutline();
}

module usb210_filter_rawAttachementOutline(h = 1)
{
  scale([25.4/90, -25.4/90, 1]) union()
  {
    linear_extrude(height=h)
      polygon([[10.495000,-293.595000],[25.495000,-294.595000],[53.495000,-294.595000],[65.665000,-295.405000],[76.495000,-294.595000],[95.495000,-294.595000],[100.916973,-294.412324],[106.521406,-293.919219],[118.028750,-292.241250],[140.495000,-287.795000],[149.589863,-285.789746],[158.948281,-283.312344],[168.388496,-280.326582],[177.728750,-276.796250],[186.787285,-272.685137],[195.382344,-267.957031],[203.332168,-262.575723],[210.455000,-256.505000],[213.531230,-252.936113],[216.342969,-248.573281],[218.883535,-243.628496],[221.146250,-238.313750],[224.811406,-227.422344],[227.285000,-217.595000],[229.736094,-203.277188],[231.413750,-188.275000],[233.585000,-158.595000],[235.495000,-136.595000],[235.495000,-127.595000],[236.495000,-117.595000],[236.495000,-81.595000],[237.495000,-66.595000],[239.405000,-37.595000],[242.585000,10.405000],[244.325000,28.405000],[246.665000,59.405000],[248.455000,77.405000],[247.495000,90.405000],[247.600020,96.716934],[248.020156,101.127969],[248.654277,104.204707],[249.401250,106.513750],[250.829219,111.095156],[251.307949,114.500723],[251.495000,119.405000],[251.305625,135.875469],[250.460000,148.126250],[248.631875,160.266406],[245.495000,176.405000],[243.768281,184.787187],[241.708750,193.287500],[239.069844,201.596563],[235.605000,209.405000],[232.951152,213.917461],[230.238594,217.617812],[227.409551,220.690508],[224.406250,223.320000],[217.645781,227.987187],[209.495000,233.095000],[183.495000,249.825000],[165.495000,260.505000],[115.495000,275.825000],[97.369531,280.804688],[78.776250,285.392500],[60.042344,289.271562],[41.495000,292.125000],[13.495000,294.405000],[-7.505000,295.405000],[-37.505000,295.405000],[-49.954531,294.673594],[-64.441250,293.033750],[-78.959844,290.934531],[-91.505000,288.825000],[-143.505000,279.055000],[-154.602656,276.681406],[-165.678750,273.888750],[-171.032051,272.043770],[-176.167969,269.744219],[-181.015840,266.873496],[-185.505000,263.315000],[-190.732500,258.022344],[-196.117500,251.828750],[-205.875000,239.405000],[-210.922500,232.607500],[-213.311250,228.950938],[-215.145000,225.405000],[-216.349844,220.614375],[-217.268750,213.922500],[-218.635000,201.405000],[-221.225000,186.405000],[-223.595000,159.405000],[-224.505000,149.405000],[-232.505000,82.405000],[-232.505000,73.405000],[-233.795000,53.405000],[-236.250000,37.178750],[-237.189687,29.207656],[-237.505000,20.405000],[-236.845000,11.405000],[-237.465000,4.405000],[-239.505000,-25.595000],[-239.505000,-34.595000],[-244.335000,-89.595000],[-246.675000,-119.595000],[-250.595000,-162.595000],[-251.495000,-171.595000],[-251.406250,-176.628750],[-250.575000,-181.595000],[-249.208418,-185.344629],[-247.003594,-189.963906],[-241.008750,-200.643750],[-229.185000,-219.595000],[-223.222500,-229.507500],[-219.934687,-234.422500],[-216.465000,-238.505000],[-212.478965,-241.879805],[-207.496719,-245.332188],[-201.800801,-248.765352],[-195.673750,-252.082500],[-183.256406,-257.981562],[-172.505000,-262.255000],[-161.505000,-265.305000],[-152.505000,-269.835000],[-119.505000,-277.475000],[-59.505000,-290.165000],[-7.505000,-295.005000],[10.495000,-293.595000]]);
  }
}
