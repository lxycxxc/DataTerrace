
#sql("update")/*更新指定的数据*/
	update #(tablename) set 
 	#for(x:setts)
 		#(for.index>0?",":"") 
 		#if(x.key!=id)/*id不允许被修改*/
 			#(x.key)
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
 	
 	where
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

#sql("save")/*添加或修改 */
	
		insert into #(tablename) (
		#for(x:maps)
			#(for.index>0?",":"") #(x.key)
		#end ) values(
		
		#for(x:maps)
			#(for.index>0?",":"")
			
			#if(x.value.indexOf("#")==0)
			  #(x.value.substring(1))
			#else
		 	  #para(x.value)
		 	#end
		#end )
#end

