
#sql("delByField")/*删除指定id的数据*/
    delete #(tablename) where
 	#for(x:wheres)
    	#(for.index>0?"and":"") #(x.key)
    	#if(x.value!=null)
			= 
			#if(x.value.indexOf("#")==0)
				#(x.value.substring(1)) 		
			#else 
				#para(x.value) 
			#end
		#end
    #end
#end


#sql("delInid")/*批量删除指定id的数据*/
	delete #(tablename) where id in(
 	#for(x:wheres)
 		#(for.index>0?",":"") #para(x)
 	#end )
#end