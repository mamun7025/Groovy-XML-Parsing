package upw_codes


class BootStrap {


    def init = { servletContext ->

        new SampleXmlParsingService().doParseXmlDoc()

    }
    def destroy = {
    }


}
