
(ns app.contents.backend.handler.helpers
    (:require [app.common.backend.api :as common]
              [candy.api              :refer [return]]
              [mongo-db.api           :as mongo-db]
              [x.user.api             :as x.user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content
  ; @param (map) request
  ; @param (namespaced map)
  ;  {:content/id (string)}
  ;
  ; @return (string)
  [request {:content/keys [id]}]
  (let [projection (common/get-document-projection :content)]
       (if-let [{:content/keys [body visibility]} (mongo-db/get-document-by-id "contents" id projection)]
               (case visibility :private (if (x.user/request->authenticated? request)
                                             (return body))
                                :public      (return body)))))

(defn fill-data
  ; @param (map) request
  ; @param (map) n
  ;
  ; @example
  ;  (fill-data {...} {:my-content {:content/id "my-content"}})
  ;  =>
  ;  {:my-content "My content"}
  ;
  ; @return (map)
  [request n]
  (letfn [(f [result k v]
             (if-let [content-id (:content/id v)]
                     (assoc result k (get-content request v))
                     (assoc result k v)))]
         (reduce-kv f {} n)))
