
(ns site.backend.handlers
  (:import  org.bson.types.ObjectId)
  (:require
    [clojure.set :refer [rename-keys]]
    [mongo-db.api      :as mongo-db]
    [pathom.api        :as pathom]
            ;[x.server-core.api :as a]
            ;[server-fruits.io :as io]
    [com.wsscode.pathom3.connect.operation :as pco :refer [defresolver defmutation]]
    [com.wsscode.pathom3.interface.smart-map :as psm]))

;; TYPE Sample

;{
;  "type/model-id" : "623c46e9fd1af39f4d0dcec8",
;  "type/name" : "Alu-Profi 7520/108 STK",

;  "type/width" : "1080",
;  "type/inner-width" : "950",

;  "type/empty-weight" : "125", start
;  "type/total-weight" : "750", end

;  "type/length" : "2000",

;  "type/height" : "360",
;  "type/inner-height" : "300"}
;

(def types (mongo-db/get-collection "types"))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn run [func env props]
  (let [params (pathom/env->params env)]
    (func params props)))

(defn get-documents-by-ids
  ([coll ids]
   (mongo-db/get-documents-by-pipeline coll [{"$match" {:_id {"$in" ids}}}]))
  ([coll ids projection]
   (mongo-db/get-documents-by-pipeline coll [{"$match"   {:_id {"$in" ids}}}
                                             {"$project" projection}])))

(defn id->obj-id [id]
  (ObjectId. id))

(defn ids->obj-ids [ids]
  (mapv id->obj-id ids))

(defn remove-nils [data]
  (vec (remove nil? data)))

(defn str->int [a]
  (Integer/parseInt a))

(defn range-query
  ([field {:keys [start end] :as params}]
   (if (empty? params)
     nil
     (cond-> {}
       start (assoc-in [field "$gte"] (str->int start))
       end   (assoc-in [field "$lte"] (str->int end)))))

  ([field-1 field-2 {:keys [start end] :as params}]
   (if (empty? params)
     nil
     (cond-> {}
       start (assoc-in [field-1 "$gte"] (str->int start))
       end   (assoc-in [field-2 "$lte"] (str->int end))))))

(defn use-pipeline! [pipeline params]
  (if (empty? params)
    {}
    {:$and (pipeline params)}))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Events ----

(defn type-by-id [type-id]
  (println "GET TYPE BY ID")
  (println "type-id: " type-id)
  (mongo-db/get-document-by-id "types" type-id))

(defn type-name [name]
  (if name
    {:type/name {"$regex"   (str ".*" name ".*")
                 "$options" "i"}}))

(defn type-weight [weight]
  (range-query :type/empty-weight :type/total-weight weight))

(defn type-load-capacity [load-capacity]
  (range-query :type/empty-weight :type/total-weight load-capacity))

(defn type-length [length]
  (range-query :type/length length))

(defn type-width [width]
  (range-query :type/width width))

(defn type-height [height]
  (range-query :type/height height))

(defn type-search-pipeline [{:keys [search width height length weight load-capacity]}]
  (remove-nils
    [(type-name search)
     (type-weight weight)
     (type-width width)
     (type-length length)
     (type-height height)
     (type-load-capacity load-capacity)]))

(defn type-search! [params projection]
  ;WARNING
  ; monger projection not working properly if the field-name is namespaced keyword
  ; workaround: use the field-name as string not keyword
  ;  {:model/name 1 :_id 0})    Nope
  ;  {"model/name" 1 "_id" 0})  Yep
  ; (println "TYPE SEARCH!")
  ; (println params projection)
  ; (println (use-pipeline! type-search-pipeline params))

  (mongo-db/get-documents-by-query "types"
    (use-pipeline! type-search-pipeline params)
    projection))

(defn model-by-id [model-id]
  (println "GET MODEL BY ID")
  (println "model-id: " model-id)
  (mongo-db/get-document-by-id "models" model-id))

(defn model-name [name]
  (if name
    {:model/name {"$regex"   (str ".*" name ".*")
                  "$options" "i"}}))

(defn model-search-pipeline [{:keys [search]}]
  (remove-nils
    [(model-name search)]))

(defn model-search! [params resolver-props]
  ; (println "MODEL SEARCH!")
  ; (println params resolver-props)
  ; (println (use-pipeline! model-search-pipeline params))
  (mongo-db/get-documents-by-query "models"
    (use-pipeline! model-search-pipeline params)
    {}))

;; ---- Events ----
;; -----------------------------------------------------------------------------



;; -----------------------------------------------------------------------------
;; ---- Resolvers ----

(defresolver search-term->type [env resolver-props]
  {:type/search (run type-search! env resolver-props)})

(defresolver type-model-id->model [{:keys [type/model-id]}]
  {:type/model-id->model (model-by-id model-id)})

; (defresolver type-id->type [env resolver-props]
  ; {:type/by-id (type-by-id )})

(defresolver search-term->model [env resolver-props]
  {:model/search (run model-search! env resolver-props)})

(defresolver model-id->model [{:keys [model/id]}]
  {:model/by-id (model-by-id id)})

(defresolver get-categories [env]
  {:category/all (mongo-db/get-collection "categories" {:category/id        1
                                                        :category/name      1
                                                        :category/thumbnail 1})})

;; ---- Resolvers ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Mutations ----

;; ---- Mutations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Register handlers ----

; @constant (vector)
(def HANDLERS [search-term->model
               model-id->model
               search-term->type
               type-model-id->model
               get-categories])

(pathom/reg-handlers! ::handlers HANDLERS)


;; ---- Register handlers ----
;; -----------------------------------------------------------------------------
