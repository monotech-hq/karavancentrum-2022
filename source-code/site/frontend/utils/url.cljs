
(ns site.frontend.utils.url
  (:require
    [ajax.url :as ajax-url]
    [clojure.string :refer [split blank? replace]]))

;; -----------------------------------------------------------------------------
;; ---- Namespace ----

(defn go-to! [url]
  (.replace (.-location js/window)
            url))

(defn decode-uri [uri]
  (.decodeURI js/window uri))

(defn route-params->search-term [route-params]
  (if (empty? (:s route-params))
    ""
    (decode-uri (:s route-params))))

(defn map->query-params [params]
  (ajax-url/params-to-str :rails params))

(defn url-query-params [url config]
  (let [query-params-str (map->query-params config)]
    (str url "?" query-params-str)))

(defn- acc-param [o v]
  (cond
    (coll? o) (vec (concat o v))
    (some? o) [o v]
    :else     v))

(defn contains-map? [key]
  (re-find #"\[.+\]" key))

(defn contains-vec? [key]
  (re-find #"\[\]" key))

(defn query-map-keys->keywords [keys]
  (mapv keyword (split keys #"\]\[|\[|\]")))

(defn query-vec-key->keyword [key]
  (keyword (replace key #"\[\]" "")))

(defn acc-keyword [n-map key value]
  (cond
    (contains-map? key) (update-in n-map (query-map-keys->keywords key) acc-param value)
    (contains-vec? key) (update n-map (query-vec-key->keyword key) acc-param [value])
    :else (update n-map (keyword key) acc-param value)))

(defn query-params->map
  "Parse `s` as query params and return a hash map."
  [url]
  (if-not (nil? (re-find #"\?" url))
    (let [query-string (last (split (decode-uri url) "?"))]
      (reduce #(let [[key value] (split %2 #"=")]
                 (acc-keyword %1 key value))
       {}
       (split (str query-string) #"&")))
    {}))
