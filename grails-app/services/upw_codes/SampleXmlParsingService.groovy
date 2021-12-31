package upw_codes

import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.xml.XmlUtil
import org.codehaus.groovy.runtime.InvokerHelper

@Transactional
class SampleXmlParsingService {


    def getChildNode( xmlDoc, Integer level ){

        List answer = new NodeList()
        for (Iterator iter = InvokerHelper.asIterator(xmlDoc); iter.hasNext(); ) {

            Object child = iter.next()
            if(level == 0){
                println()
                println("----> New doc starting...")
            }
            println("level->" + level + " value: " + child.toString())

            if ( child instanceof Node ) {
                Node childNode = (Node) child
                List children = getChildNode( childNode, level+1 )
                answer.add(childNode)
                if ( children.size() > 1 || ( children.size() == 1 && !(children.get(0) instanceof String) ) ) answer.addAll(children)

            } else if (child instanceof String) {
                answer.add(child)
            }
        }
        return answer

    }


    def printXmlDocRecursively( articlesXmlDoc ){
        println("------------------Recursively Start")
        def returnList = this.getChildNode( articlesXmlDoc, 0 )
        println(returnList)
        println("------------------Recursively End")
    }


    def deleteXmlNode(){
//        articles.article
//                .findAll { it.author.'@id'.text() != "3" }
//                .each { articles.remove(it) }
    }

    def doParseXmlDoc() {


        def xmlFileStream = this.class.classLoader.getResourceAsStream('articles.xml')
        def articles = new XmlParser().parse(xmlFileStream)

        // recursive
        this.printXmlDocRecursively( articles )

        println("\n\n\n")
        // iterating XML blocks // 2 level
        articles.each {
            def node = it
            println(node)
            node.each { inIt->
                def innerNode = inIt
                println(innerNode)
            }
        }

        articles.article
                .findAll { it.author.'@id'.text() != "3" }
                .each { articles.remove(it) }

        println(articles.children().size())



        // Construct a FileWriter object and assigns it to the variable 'writer'
        // def writer = new FileWriter("D:\\mijn\\Web.config")
        def writer = new FileWriter("E:\\ME_NEW_2021\\CV_JOB\\UPW_Codes\\SampleXmlParsing\\src\\main\\resources\\articles_up.xml")
        // Write the changes to the file
        XmlUtil.serialize(articles, writer)
        // Close file
        writer.close()


    }



}
